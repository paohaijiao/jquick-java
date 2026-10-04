package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.CampaignFraudRules;
import com.github.paohaijiao.demo.model.CampaignFraudDecision;
import com.github.paohaijiao.demo.model.CampaignOrder;

/**
 * 场景 26：营销活动订单防刷单风控
 * <p>
 * 六维风险评分（账户龄/频次/设备聚集/身份/券单空包/客单偏离）叠加硬规则拦截。
 */
public class CampaignFraudService {

    private final CampaignFraudRules rules;

    public CampaignFraudService(CampaignFraudRules rules) {
        this.rules = rules;
    }

    public CampaignFraudDecision check(CampaignOrder order) {
        CampaignFraudDecision d = new CampaignFraudDecision();
        d.setOrderId(order.getOrderId());

        int ageScore = rules.scoreAccountAge(order.getAccountAgeDays());
        int freqScore = rules.scoreFrequency(order.getOrderCount1h(), order.getOrderCount24h());
        int deviceScore = rules.scoreDevice(order.getDeviceAccountCount24h(), order.getIpAccountCount24h());
        int identityScore = rules.scoreIdentity(order.isRealName(),
                order.isPaymentConsistent(), order.isVirtualAddress());
        int couponScore = rules.scoreCoupon(order.getCouponOrderCount7d(), order.getEmptyPackageRate());
        int deviationScore = rules.scoreDeviation(order.getAmountDeviationPercent());

        d.addDetail("账户龄: " + ageScore + " / 20");
        d.addDetail("下单频次: " + freqScore + " / 25");
        d.addDetail("设备与IP聚集: " + deviceScore + " / 20");
        d.addDetail("身份与地址: " + identityScore + " / 15");
        d.addDetail("券单与空包: " + couponScore + " / 10");
        d.addDetail("客单价偏离: " + deviationScore + " / 10");

        int total = ageScore + freqScore + deviceScore + identityScore + couponScore + deviationScore;
        if (total > 100) {
            total = 100;
        }

        boolean devicePoolHit = !order.isRealName() && order.getDeviceAccountCount24h() >= 5;
        boolean scriptHit = order.getOrderCount1h() >= 5 && order.getAmountDeviationPercent() >= 400;
        boolean hardHit = devicePoolHit || scriptHit;
        d.setHardRuleHit(hardHit);
        if (devicePoolHit) {
            d.addRiskTag("黑产设备池：未实名账户聚集同一设备");
        }
        if (scriptHit) {
            d.addRiskTag("秒杀脚本特征：1 小时高频下单且客单价严重偏离");
        }

        collectSoftTags(d, order);

        if (hardHit || total >= 70) {
            d.setAction("REJECT");
        } else if (total >= 40) {
            d.setAction("REVIEW");
        } else {
            d.setAction("PASS");
        }
        d.setRiskScore(total);
        return d;
    }

    private void collectSoftTags(CampaignFraudDecision d, CampaignOrder o) {
        if (o.getAccountAgeDays() < 1) {
            d.addRiskTag("新注册账户（不满 1 天）");
        } else if (o.getAccountAgeDays() < 7) {
            d.addRiskTag("注册未满 7 天");
        }
        if (o.getOrderCount1h() >= 5) {
            d.addRiskTag("1 小时内高频下单");
        }
        if (o.getOrderCount24h() >= 15) {
            d.addRiskTag("24 小时下单频次异常");
        }
        if (o.getDeviceAccountCount24h() >= 5) {
            d.addRiskTag("同设备关联多账户");
        }
        if (o.getIpAccountCount24h() >= 10) {
            d.addRiskTag("同 IP 关联多账户");
        }
        if (!o.isRealName()) {
            d.addRiskTag("账户未实名认证");
        }
        if (!o.isPaymentConsistent()) {
            d.addRiskTag("付款人与下单人不一致");
        }
        if (o.isVirtualAddress()) {
            d.addRiskTag("虚拟/集中代收收货地址");
        }
        if (o.getCouponOrderCount7d() >= 10) {
            d.addRiskTag("7 天用券订单异常集中");
        }
        if (o.getEmptyPackageRate() >= 50) {
            d.addRiskTag("历史空包率高");
        }
        if (o.getAmountDeviationPercent() >= 400) {
            d.addRiskTag("客单价严重偏离历史水平");
        }
    }
}
