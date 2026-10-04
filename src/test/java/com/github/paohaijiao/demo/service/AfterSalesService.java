package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.AfterSalesRules;
import com.github.paohaijiao.demo.model.AfterSalesDecision;
import com.github.paohaijiao.demo.model.AfterSalesRequest;

/**
 * 场景 27：售后退款/退货/换货资格判定
 * <p>
 * 判定优先级：质量问题 → 类目禁退 → 换货 → 七天无理由。
 */
public class AfterSalesService {

    private final AfterSalesRules rules;

    public AfterSalesService(AfterSalesRules rules) {
        this.rules = rules;
    }

    public AfterSalesDecision review(AfterSalesRequest req) {
        AfterSalesDecision d = new AfterSalesDecision();
        d.setOrderId(req.getOrderId());

        boolean qualityClaim = "QUALITY".equals(req.getReasonType())
                || "DAMAGED".equals(req.getProductCondition());

        if (qualityClaim) {
            handleQuality(req, d);
            return d;
        }

        if ("CUSTOM".equals(req.getProductCategory())
                || "VIRTUAL".equals(req.getProductCategory())
                || "FRESH".equals(req.getProductCategory())) {
            reject(d, categoryText(req.getProductCategory()));
            return d;
        }

        if ("SIZE".equals(req.getReasonType()) || "MISMATCH".equals(req.getReasonType())) {
            handleExchange(req, d);
            return d;
        }

        handleSevenDay(req, d);
        return d;
    }

    private void handleQuality(AfterSalesRequest req, AfterSalesDecision d) {
        boolean eligible = rules.qualityEligible(req.getDaysSinceReceived(),
                req.isQualityProof(), req.isShopResponsible());
        if (!eligible) {
            d.setAction("REJECT");
            d.setRefundRatio(0.0);
            d.setFreightPayer("NONE");
            d.addReason("超出 15 天质量异议期或举证不足，不予受理质量退货");
            return;
        }
        d.setAction("RETURN_REFUND");
        d.setRefundRatio(1.0);
        d.setFreightPayer("MERCHANT");
        if (req.isShopResponsible()) {
            d.addReason("商家责任认定成立，办理全额退货退款，退货运费由商家承担");
        } else {
            d.addReason("质量问题凭证核验通过，办理全额退货退款，退货运费由商家承担");
        }
    }

    private String categoryText(String category) {
        if ("FRESH".equals(category)) {
            return "生鲜易腐商品不支持七天无理由退货（质量问题除外）";
        }
        if ("CUSTOM".equals(category)) {
            return "定制商品不支持七天无理由退货";
        }
        if ("VIRTUAL".equals(category)) {
            return "虚拟商品一经交付不支持退货";
        }
        return "该商品类目不支持无理由退货";
    }

    private void reject(AfterSalesDecision d, String categoryText) {
        d.setAction("REJECT");
        d.setRefundRatio(0.0);
        d.setFreightPayer("NONE");
        d.addReason(categoryText);
    }

    private void handleExchange(AfterSalesRequest req, AfterSalesDecision d) {
        boolean eligible = rules.exchangeEligible(req.getReasonType(), req.getDaysSinceReceived());
        if (!eligible) {
            d.setAction("REJECT");
            d.setRefundRatio(0.0);
            d.setFreightPayer("NONE");
            d.addReason("已超过换货申请期限（尺码问题 15 天、描述不符 30 天）");
            return;
        }
        d.setAction("EXCHANGE");
        d.setRefundRatio(0.0);
        d.setFreightPayer(rules.freightPayer(req.getReasonType()));
        if ("MISMATCH".equals(req.getReasonType())) {
            d.addReason("商品与描述不符，30 天内支持换货，往返运费由商家承担");
        } else {
            d.addReason("尺码不符 15 天内支持换货，换货运费由买家承担");
        }
    }

    private void handleSevenDay(AfterSalesRequest req, AfterSalesDecision d) {
        boolean eligible = rules.sevenDayEligible(req.getDaysSinceReceived(),
                "UNUSED".equals(req.getProductCondition()),
                req.isSealedIntact(), req.isImpactResale());
        if (!eligible) {
            d.setAction("REJECT");
            d.setRefundRatio(0.0);
            d.setFreightPayer("NONE");
            d.addReason("不符合七天无理由退货条件（超期、已使用、密封破损或影响二次销售）");
            return;
        }
        d.setAction("RETURN_REFUND");
        d.setRefundRatio(1.0);
        d.setFreightPayer("BUYER");
        d.addReason("符合七天无理由退货条件，办理全额退款，退货运费由买家承担");
    }
}
