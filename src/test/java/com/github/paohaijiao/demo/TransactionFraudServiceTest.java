package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.BankTransaction;
import com.github.paohaijiao.demo.model.FraudDecision;
import com.github.paohaijiao.demo.service.TransactionFraudService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 15 生产级样板：银行卡交易反欺诈实时风控
 * <p>
 * 覆盖 6 组交易形态：日常正常交易、深夜境外高危、异地报备出行、
 * 夜间新设备中风险、新账户高频失败、受信商户白名单。
 */
public class TransactionFraudServiceTest {

    private TransactionFraudService service;

    @Before
    public void setUp() {
        TransactionFraudRules rules = JQuickJava.create()
                .createApi(TransactionFraudRules.class, "demo/transaction-fraud.xml");
        this.service = new TransactionFraudService(rules);
    }

    /**
     * 日常小额 POS 交易：全部风险因子为 0 → 放行
     */
    @Test
    public void shouldPassNormalTransaction() {
        BankTransaction txn = txn("TXN-1501", 200.0, 14, "POS", false, false, false,
                1, 200.0, false, false, true, 0, 400, 5000.0, false);

        FraudDecision decision = service.evaluate(txn);

        Assert.assertEquals(0, decision.getRiskScore());
        Assert.assertEquals("LOW", decision.getRiskLevel());
        Assert.assertEquals("PASS", decision.getAction());
        Assert.assertFalse(decision.isNeedAlert());
        Assert.assertTrue(decision.getIndicators().isEmpty());
    }

    /**
     * 深夜境外线上大额交易，多因子叠加 → 100 分拦截并预警
     */
    @Test
    public void shouldBlockHighRiskOverseasNightTransaction() {
        BankTransaction txn = txn("TXN-1502", 30000.0, 2, "ONLINE", true, true, false,
                6, 60000.0, true, true, false, 1, 400, 8000.0, false);

        FraudDecision decision = service.evaluate(txn);

        Assert.assertEquals(100, decision.getRiskScore());
        Assert.assertEquals("HIGH", decision.getRiskLevel());
        Assert.assertEquals("BLOCK", decision.getAction());
        Assert.assertTrue(decision.isNeedAlert());
        Assert.assertTrue(decision.getIndicators().contains("境外交易"));
        Assert.assertTrue(decision.getIndicators().contains("深夜时段交易（2 时）"));
        Assert.assertTrue(decision.getIndicators().contains("交易设备变更"));
        Assert.assertTrue(decision.getIndicators().contains("ONLINE 渠道无卡交易"));
        Assert.assertTrue(decision.getIndicators().contains("金额显著偏离历史消费习惯"));
    }

    /**
     * 异地报备出行的移动端无卡小额交易 → 29 分放行
     */
    @Test
    public void shouldPassReportedTravelModeTransaction() {
        BankTransaction txn = txn("TXN-1503", 6000.0, 20, "MOBILE", false, true, true,
                2, 8000.0, true, false, false, 0, 200, 5000.0, false);

        FraudDecision decision = service.evaluate(txn);

        Assert.assertEquals(29, decision.getRiskScore());
        Assert.assertEquals("LOW", decision.getRiskLevel());
        Assert.assertEquals("PASS", decision.getAction());
        Assert.assertFalse(decision.isNeedAlert());
        Assert.assertTrue(decision.getIndicators().contains("异地交易（已报备出行）"));
        Assert.assertTrue(decision.getIndicators().contains("MOBILE 渠道无卡交易"));
    }

    /**
     * 深夜新设备新商户线上交易 → 65 分中风险，强验证不预警
     */
    @Test
    public void shouldStepUpMediumRiskNightTransaction() {
        BankTransaction txn = txn("TXN-1504", 12000.0, 23, "ONLINE", false, false, false,
                4, 30000.0, true, true, false, 0, 100, 8000.0, false);

        FraudDecision decision = service.evaluate(txn);

        Assert.assertEquals(65, decision.getRiskScore());
        Assert.assertEquals("MEDIUM", decision.getRiskLevel());
        Assert.assertEquals("STEP_UP", decision.getAction());
        Assert.assertFalse(decision.isNeedAlert());
        Assert.assertTrue(decision.getIndicators().contains("近 1 小时交易 4 笔"));
        Assert.assertTrue(decision.getIndicators().contains("深夜时段交易（23 时）"));
    }

    /**
     * 新开户 3 天、鉴权连续失败、大额偏离 → 72 分中风险强验证
     */
    @Test
    public void shouldStepUpNewAccountWithAuthFailures() {
        BankTransaction txn = txn("TXN-1505", 6000.0, 15, "ONLINE", false, false, false,
                4, 20000.0, true, false, false, 4, 3, 1000.0, false);

        FraudDecision decision = service.evaluate(txn);

        Assert.assertEquals(72, decision.getRiskScore());
        Assert.assertEquals("MEDIUM", decision.getRiskLevel());
        Assert.assertEquals("STEP_UP", decision.getAction());
        Assert.assertTrue(decision.getIndicators().contains("新开户账户（3 天）"));
        Assert.assertTrue(decision.getIndicators().contains("近 24 小时鉴权失败 4 次"));
    }

    /**
     * 虽命中多项风险因子，但商户在白名单 → 直接放行
     */
    @Test
    public void shouldBypassForTrustedMerchant() {
        BankTransaction txn = txn("TXN-1506", 40000.0, 1, "ONLINE", true, true, false,
                8, 80000.0, true, true, false, 3, 2, 3000.0, true);

        FraudDecision decision = service.evaluate(txn);

        Assert.assertEquals(0, decision.getRiskScore());
        Assert.assertEquals("LOW", decision.getRiskLevel());
        Assert.assertEquals("PASS", decision.getAction());
        Assert.assertFalse(decision.isNeedAlert());
        Assert.assertEquals(1, decision.getIndicators().size());
        Assert.assertTrue(decision.getIndicators().contains("受信商户白名单交易，直接放行"));
    }

    private BankTransaction txn(String txnNo, double amount, int hour, String channel,
                                boolean overseas, boolean crossCity, boolean travelMode,
                                int count1h, double amount1h, boolean newMerchant,
                                boolean deviceChanged, boolean cardPresent, int failCount24h,
                                int accountAgeDays, double historicalMax, boolean trustedMerchant) {
        BankTransaction txn = new BankTransaction();
        txn.setTxnNo(txnNo);
        txn.setCardNo("6222 **** **** 0001");
        txn.setAmount(amount);
        txn.setTxnHour(hour);
        txn.setChannel(channel);
        txn.setOverseas(overseas);
        txn.setCrossCity(crossCity);
        txn.setTravelMode(travelMode);
        txn.setTxnCount1h(count1h);
        txn.setTxnAmount1h(amount1h);
        txn.setNewMerchant(newMerchant);
        txn.setDeviceChanged(deviceChanged);
        txn.setCardPresent(cardPresent);
        txn.setFailCount24h(failCount24h);
        txn.setAccountAgeDays(accountAgeDays);
        txn.setHistoricalMaxAmount(historicalMax);
        txn.setTrustedMerchant(trustedMerchant);
        return txn;
    }
}
