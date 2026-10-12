package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.PresaleFinalPaymentRules;
import com.github.paohaijiao.demo.model.PresaleFinalPaymentDecision;
import com.github.paohaijiao.demo.model.PresaleOrder;

/**
 * 场景 30：预售尾款资格、定金处置与商家履约校验
 */
public class PresaleFinalPaymentService {

    private final PresaleFinalPaymentRules rules;

    public PresaleFinalPaymentService(PresaleFinalPaymentRules rules) {
        this.rules = rules;
    }

    public PresaleFinalPaymentDecision process(PresaleOrder order) {
        PresaleFinalPaymentDecision d = new PresaleFinalPaymentDecision();
        d.setOrderId(order.getOrderId());
        int available = rules.availableStock(order.getStockOnHand(), order.getReservedStock());
        d.setAvailableStock(available);

        boolean eligible = rules.merchantEligible(order.getMerchantCreditScore(),
                order.getMerchantBreaches(), order.isMerchantBondPaid());
        d.setMerchantEligible(eligible);
        if (!eligible) {
            d.setAction("BLOCKED");
            d.setPayableAmount(0.0);
            d.setDepositTreatment("REFUNDABLE");
            if (order.getMerchantCreditScore() < 80) {
                d.addReason("商家信用分 " + order.getMerchantCreditScore() + " 低于 80 分预售门槛");
            }
            if (order.getMerchantBreaches() >= 1) {
                d.addReason("商家存在 " + order.getMerchantBreaches() + " 次历史预售违约记录");
            }
            if (!order.isMerchantBondPaid()) {
                d.addReason("商家未缴纳履约保证金");
            }
            d.addReason("商家预售资格中止，订单关闭，定金原路退还买家");
            return d;
        }

        String treatment = rules.depositTreatment(order.getWindowStatus(),
                order.isIdentityVerified(), order.isCancelledBeforeWindow());

        if (order.isCancelledBeforeWindow()) {
            d.setAction("CANCELLED");
            d.setPayableAmount(0.0);
            d.setDepositTreatment("REFUNDABLE");
            d.addReason("尾款窗口开启前取消订单，定金全额退还");
            return d;
        }

        if (order.getWindowStatus() == 2) {
            d.setAction("FORFEIT");
            d.setPayableAmount(0.0);
            d.setDepositTreatment("FORFEIT");
            d.addReason("尾款支付窗口已关闭且未补款，定金按预售协议不予退还");
            return d;
        }

        if (order.getWindowStatus() == 0) {
            d.setAction("WAITING");
            d.setPayableAmount(0.0);
            d.setDepositTreatment("HOLD");
            d.addReason("尾款支付窗口尚未开启，定金由平台暂存");
            return d;
        }

        if (!rules.canPayBalance(order.getWindowStatus(), order.isIdentityVerified())) {
            d.setAction("HOLD");
            d.setPayableAmount(0.0);
            d.setDepositTreatment("HOLD");
            d.addReason("买家尚未完成实名认证，补全实名后即可支付尾款");
            return d;
        }

        if (available < order.getDemandQty()) {
            d.setAction("WAITING");
            d.setPayableAmount(0.0);
            d.setDepositTreatment("DEDUCT");
            d.addReason("扣减预售锁定库存后仅剩 " + available
                    + " 件现货，不足本单 " + order.getDemandQty()
                    + " 件需求，按尾款支付顺序排队等待调拨补货");
            return d;
        }

        d.setAction("PAYABLE");
        d.setPayableAmount(order.getBalanceAmount());
        d.setDepositTreatment(treatment);
        d.addReason("尾款窗口进行中且库存充足，应支付尾款 "
                + order.getBalanceAmount() + " 元，定金作履约担保");
        return d;
    }
}
