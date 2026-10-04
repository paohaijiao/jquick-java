package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.DistributionCommissionRules;
import com.github.paohaijiao.demo.model.DistributionCommissionDecision;
import com.github.paohaijiao.demo.model.DistributionOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 29：分销自购 + 直推 + 间推 + 团长服务费多级分润
 */
public class DistributionCommissionService {

    private static final BigDecimal TOTAL_CAP_RATIO = new BigDecimal("0.30");

    private final DistributionCommissionRules rules;

    public DistributionCommissionService(DistributionCommissionRules rules) {
        this.rules = rules;
    }

    public DistributionCommissionDecision settle(DistributionOrder order) {
        DistributionCommissionDecision d = new DistributionCommissionDecision();
        d.setOrderId(order.getOrderId());

        if (order.isRefunded()) {
            d.setBaseAmount(0.0);
            d.setSelfCommission(0.0);
            d.setDirectCommission(0.0);
            d.setIndirectCommission(0.0);
            d.setGroupBonus(0.0);
            d.setTotalCommission(0.0);
            d.setStatus("FORFEITED");
            d.addBreakdown("订单已退款，各级分润全部不予结算");
            return d;
        }

        BigDecimal base = money(order.getOrderAmount()
                * rules.baseFactor(order.getPromotionCostRate()));
        BigDecimal self = money(base.doubleValue() * rules.categoryRate(order.getProductCategory())
                * rules.levelMultiplier(order.getDistributorLevel()));
        BigDecimal direct = money(base.doubleValue()
                * rules.directReferRate(order.getUplineLevel()));
        BigDecimal indirect = money(base.doubleValue()
                * rules.indirectReferRate(order.getRootLevel()));
        BigDecimal group = money(base.doubleValue()
                * rules.groupBonusRate(order.isGroupLeader(), order.getGroupCount30d()));

        BigDecimal total = self.add(direct).add(indirect).add(group);
        BigDecimal cap = money(base.multiply(TOTAL_CAP_RATIO).doubleValue());
        boolean capped = total.compareTo(cap) > 0;
        if (capped) {
            total = cap;
        }

        d.setBaseAmount(base.doubleValue());
        d.setSelfCommission(self.doubleValue());
        d.setDirectCommission(direct.doubleValue());
        d.setIndirectCommission(indirect.doubleValue());
        d.setGroupBonus(group.doubleValue());
        d.setTotalCommission(total.doubleValue());
        d.setCapped(capped);

        d.addBreakdown("可佣基数: " + base.toPlainString() + " 元");
        d.addBreakdown("自购佣金: " + self.toPlainString() + " 元");
        d.addBreakdown("直推奖励: " + direct.toPlainString() + " 元");
        d.addBreakdown("间推奖励: " + indirect.toPlainString() + " 元");
        d.addBreakdown("团长服务费: " + group.toPlainString() + " 元");
        if (capped) {
            d.addBreakdown("多级分润合计超过可佣基数 30%，已按上限 " + cap.toPlainString() + " 元熔断");
        }

        if (order.getOrderAmount() >= 50000) {
            d.setStatus("PENDING");
            d.addBreakdown("单笔金额超 5 万元，转人工核验交易真实性后再行结算");
        } else {
            d.setStatus("SETTLED");
        }
        return d;
    }

    private BigDecimal money(double v) {
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.HALF_UP);
    }
}
