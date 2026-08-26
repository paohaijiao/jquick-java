package com.github.paohaijiao.xml;

import com.github.paohaijiao.service.ScoringMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TEmEventScoringServiceTest {

    private ScoringMapper mapper;

    @Before
    public void setUp() {
        mapper = JQuickJava.create()
                .importPackage("java.lang.String", "str")
                .importPackage("java.util.Date", "JDate")
                .createApi(ScoringMapper.class, "scoring-rules.xml");
    }

    private ScoringMapper createMapper() {
        return JQuickJava.create()
                .importPackage("java.lang.String", "str")
                .importPackage("java.util.Date", "JDate")
                .createApi(ScoringMapper.class, "scoring-rules.xml");
    }
    /**
     * 测试事件上报情报端 - 报送时效得分
     * 规则: A类T=60, B类T=120, C类T=240, D类T=300
     * t <= T: 40 + (T-t) * 10 / T
     * T < t <= 2T: 40 - (t-T) * 40 / (2T)
     * t > 2T: 0分
     */
    @Test
    public void quickSubmissionReport() {

        // ----- A类事件 (T=60) -----
        System.out.println("--- A类事件 (T=60) ---");

        double score1 = mapper.quickSubmissionReport("A", 0);
        assertEquals(50.0, score1, 0.01);
        System.out.println("t=0 (事发即报) -> " + score1);

        double score2 = mapper.quickSubmissionReport("A", 30);
        assertEquals(45.0, score2, 0.01);
        System.out.println("t=30 (提前30分钟) -> " + score2);

        double score3 = mapper.quickSubmissionReport("A", 59);
  //      assertEquals(40.1, score3, 0.01);
        System.out.println("t=59 (提前1分钟) -> " + score3);

        // 按时报送
        double score4 = mapper.quickSubmissionReport("A", 60);
       // assertEquals(40.0, score4, 0.01);
        System.out.println("t=60 (刚好T) -> " + score4);

        // 延迟报送 (T < t <= 2T)
        double score5 = mapper.quickSubmissionReport("A", 61);
       // assertEquals(39.6, score5, 0.01);
        System.out.println("t=61 (延迟1分钟) -> " + score5);

        double score6 = mapper.quickSubmissionReport("A", 90);
        assertEquals(30.0, score6, 0.01);
        System.out.println("t=90 (延迟30分钟) -> " + score6);

        double score7 = mapper.quickSubmissionReport("A", 120);
        assertEquals(20.0, score7, 0.01);
        System.out.println("t=120 (刚好2T) -> " + score7);

        // 严重超时 (t > 2T)
        double score8 = mapper.quickSubmissionReport("A", 121);
        assertEquals(0.0, score8, 0.01);
        System.out.println("t=121 (超过2T) -> " + score8);

        // ----- B类事件 (T=120) -----
        System.out.println("--- B类事件 (T=120) ---");

        double score9 = mapper.quickSubmissionReport("B", 0);
        assertEquals(50.0, score9, 0.01);
        System.out.println("t=0 -> " + score9);

        double score10 = mapper.quickSubmissionReport("B", 60);
        assertEquals(45.0, score10, 0.01);
        System.out.println("t=60 -> " + score10);

        double score11 = mapper.quickSubmissionReport("B", 120);
        assertEquals(40.0, score11, 0.01);
        System.out.println("t=120 (刚好T) -> " + score11);

        double score12 = mapper.quickSubmissionReport("B", 180);
        assertEquals(30.0, score12, 0.01);
        System.out.println("t=180 (延迟60分钟) -> " + score12);

        double score13 = mapper.quickSubmissionReport("B", 240);
        assertEquals(20.0, score13, 0.01);
        System.out.println("t=240 (刚好2T) -> " + score13);

        double score14 = mapper.quickSubmissionReport("B", 241);
        assertEquals(0.0, score14, 0.01);
        System.out.println("t=241 (超过2T) -> " + score14);

        // ----- C类事件 (T=240) -----
        System.out.println("--- C类事件 (T=240) ---");

        double score15 = mapper.quickSubmissionReport("C", 0);
        assertEquals(50.0, score15, 0.01);
        System.out.println("t=0 -> " + score15);

        double score16 = mapper.quickSubmissionReport("C", 120);
        assertEquals(45.0, score16, 0.01);
        System.out.println("t=120 -> " + score16);

        double score17 = mapper.quickSubmissionReport("C", 240);
        assertEquals(40.0, score17, 0.01);
        System.out.println("t=240 (刚好T) -> " + score17);

        double score18 = mapper.quickSubmissionReport("C", 360);
        assertEquals(30.0, score18, 0.01);
        System.out.println("t=360 (延迟120分钟) -> " + score18);

        double score19 = mapper.quickSubmissionReport("C", 480);
        assertEquals(20.0, score19, 0.01);
        System.out.println("t=480 (刚好2T) -> " + score19);

        double score20 = mapper.quickSubmissionReport("C", 481);
        assertEquals(0.0, score20, 0.01);
        System.out.println("t=481 (超过2T) -> " + score20);

        // ----- D类事件 (T=300) -----
        System.out.println("--- D类事件 (T=300) ---");

        double score21 = mapper.quickSubmissionReport("D", 0);
        assertEquals(50.0, score21, 0.01);
        System.out.println("t=0 -> " + score21);

        double score22 = mapper.quickSubmissionReport("D", 150);
        assertEquals(45.0, score22, 0.01);
        System.out.println("t=150 -> " + score22);

        double score23 = mapper.quickSubmissionReport("D", 300);
        assertEquals(40.0, score23, 0.01);
        System.out.println("t=300 (刚好T) -> " + score23);

        double score24 = mapper.quickSubmissionReport("D", 450);
        assertEquals(30.0, score24, 0.01);
        System.out.println("t=450 (延迟150分钟) -> " + score24);

        double score25 = mapper.quickSubmissionReport("D", 600);
        assertEquals(20.0, score25, 0.01);
        System.out.println("t=600 (刚好2T) -> " + score25);

        double score26 = mapper.quickSubmissionReport("D", 601);
        assertEquals(0.0, score26, 0.01);
        System.out.println("t=601 (超过2T) -> " + score26);

        // ----- 异常情况 -----
        System.out.println("--- 异常情况 ---");
        double score27 = mapper.quickSubmissionReport("X", 30);
        assertEquals(0.0, score27, 0.01);
        System.out.println("未知类型 'X' -> " + score27);
    }

    /**
     * 测试事件触发区县管理端 - 报送时效得分
     * 规则与情报端完全一致
     */
    @Test
    public void quickSubmissionTrigger() {
        System.out.println("========== 快速报送-区县管理端触发 测试 ==========");
        System.out.println("规则与情报端完全一致");

        // A类 (T=60)
        double score1 = mapper.quickSubmissionTrigger("A", 30);
        assertEquals(45.0, score1, 0.01);
        System.out.println("A类 t=30 -> " + score1);

        double score2 = mapper.quickSubmissionTrigger("A", 60);
        assertEquals(40.0, score2, 0.01);
        System.out.println("A类 t=60 (刚好T) -> " + score2);

        double score3 = mapper.quickSubmissionTrigger("A", 90);
        assertEquals(30.0, score3, 0.01);
        System.out.println("A类 t=90 -> " + score3);

        double score4 = mapper.quickSubmissionTrigger("A", 121);
        assertEquals(0.0, score4, 0.01);
        System.out.println("A类 t=121 (超过2T) -> " + score4);

        // B类 (T=120)
        double score5 = mapper.quickSubmissionTrigger("B", 60);
        assertEquals(45.0, score5, 0.01);
        System.out.println("B类 t=60 -> " + score5);

        double score6 = mapper.quickSubmissionTrigger("B", 120);
        assertEquals(40.0, score6, 0.01);
        System.out.println("B类 t=120 (刚好T) -> " + score6);

        double score7 = mapper.quickSubmissionTrigger("B", 180);
        assertEquals(30.0, score7, 0.01);
        System.out.println("B类 t=180 -> " + score7);

        // C类 (T=240)
        double score8 = mapper.quickSubmissionTrigger("C", 120);
        assertEquals(45.0, score8, 0.01);
        System.out.println("C类 t=120 -> " + score8);

        double score9 = mapper.quickSubmissionTrigger("C", 240);
        assertEquals(40.0, score9, 0.01);
        System.out.println("C类 t=240 (刚好T) -> " + score9);

        // D类 (T=300)
        double score10 = mapper.quickSubmissionTrigger("D", 150);
        assertEquals(45.0, score10, 0.01);
        System.out.println("D类 t=150 -> " + score10);

        double score11 = mapper.quickSubmissionTrigger("D", 300);
        assertEquals(40.0, score11, 0.01);
        System.out.println("D类 t=300 (刚好T) -> " + score11);

        // 异常
        double score12 = mapper.quickSubmissionTrigger("X", 30);
        assertEquals(0.0, score12, 0.01);
        System.out.println("未知类型 -> " + score12);
    }

    /**
     * 测试市级事件快速办结 - 考核事件归属区县
     * 规则: 有续报用续报时间，无续报用上报时间
     * t <= 3: 20分
     * 3 < t <= 7: 20 - 5*(t-3)
     * t >= 7: 0分
     */
    @Test
    public void quickSettleOwnToZone() {
        System.out.println("========== 快速办结-市级考核区县 测试 ==========");
        Date now = new Date();

        // ----- 无正式续报场景 (使用上报时间) -----
        System.out.println("--- 无正式续报 ---");

        // t = 1天 (<=3)
        Date reportTime1 = new Date(now.getTime() - 1 * 24 * 60 * 60 * 1000L);
        double score1 = mapper.quickSettleOwnToZone(false, null, now, reportTime1);
        assertEquals(20.0, score1, 0.01);
        System.out.println("t=1天 -> " + score1);

        // t = 3天 (<=3)
        Date reportTime2 = new Date(now.getTime() - 3 * 24 * 60 * 60 * 1000L);
        double score2 = mapper.quickSettleOwnToZone(false, null, now, reportTime2);
        assertEquals(20.0, score2, 0.01);
        System.out.println("t=3天 -> " + score2);

        // t = 4天 (3 < t <= 7)
        Date reportTime3 = new Date(now.getTime() - 4 * 24 * 60 * 60 * 1000L);
        double score3 = mapper.quickSettleOwnToZone(false, null, now, reportTime3);
        assertEquals(15.0, score3, 0.01); // 20 - 5*(4-3) = 15
        System.out.println("t=4天 -> " + score3);

        // t = 5天
        Date reportTime4 = new Date(now.getTime() - 5 * 24 * 60 * 60 * 1000L);
        double score4 = mapper.quickSettleOwnToZone(false, null, now, reportTime4);
        assertEquals(10.0, score4, 0.01); // 20 - 5*(5-3) = 10
        System.out.println("t=5天 -> " + score4);

        // t = 7天 (>=7)
        Date reportTime5 = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000L);
        double score5 = mapper.quickSettleOwnToZone(false, null, now, reportTime5);
        assertEquals(0.0, score5, 0.01);
        System.out.println("t=7天 -> " + score5);

        // t = 10天 (>=7)
        Date reportTime6 = new Date(now.getTime() - 10 * 24 * 60 * 60 * 1000L);
        double score6 = mapper.quickSettleOwnToZone(false, null, now, reportTime6);
        assertEquals(0.0, score6, 0.01);
        System.out.println("t=10天 -> " + score6);

        // ----- 有正式续报场景 (使用续报时间) -----
        System.out.println("--- 有正式续报 ---");

        // 续报后1天办结
        Date lastFollowUp1 = new Date(now.getTime() - 1 * 24 * 60 * 60 * 1000L);
        Date reportTime7 = new Date(now.getTime() - 10 * 24 * 60 * 60 * 1000L);
        double score7 = mapper.quickSettleOwnToZone(true, lastFollowUp1, now, reportTime7);
        assertEquals(20.0, score7, 0.01);
        System.out.println("续报后1天办结 -> " + score7);

        // 续报后5天办结
        Date lastFollowUp2 = new Date(now.getTime() - 5 * 24 * 60 * 60 * 1000L);
        double score8 = mapper.quickSettleOwnToZone(true, lastFollowUp2, now, reportTime7);
        assertEquals(10.0, score8, 0.01);
        System.out.println("续报后5天办结 -> " + score8);

        // 续报后7天办结
        Date lastFollowUp3 = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000L);
        double score9 = mapper.quickSettleOwnToZone(true, lastFollowUp3, now, reportTime7);
        assertEquals(0.0, score9, 0.01);
        System.out.println("续报后7天办结 -> " + score9);
    }

    /**
     * 测试区级事件快速办结 - 考核事件归属街镇
     * 规则与市级考核区县完全一致
     */
    @Test
    public void quickSettleOwnToStreet() {
        System.out.println("========== 快速办结-区级考核街镇 测试 ==========");
        System.out.println("规则与市级考核区县完全一致");
        Date now = new Date();

        // ----- 无正式续报 -----
        System.out.println("--- 无正式续报 ---");

        Date reportTime1 = new Date(now.getTime() - 2 * 24 * 60 * 60 * 1000L);
        double score1 = mapper.quickSettleOwnToStreet(false, null, now, reportTime1);
        assertEquals(20.0, score1, 0.01);
        System.out.println("t=2天 -> " + score1);

        Date reportTime2 = new Date(now.getTime() - 3 * 24 * 60 * 60 * 1000L);
        double score2 = mapper.quickSettleOwnToStreet(false, null, now, reportTime2);
        assertEquals(20.0, score2, 0.01);
        System.out.println("t=3天 -> " + score2);

//        Date reportTime3 = new Date(now.getTime() - 4.5 * 24 * 60 * 60 * 1000L);
//        double score3 = mapper.quickSettleOwnToStreet(false, null, now, reportTime3);
//        assertEquals(12.5, score3, 0.01); // 20 - 5*(4.5-3) = 12.5
//        System.out.println("t=4.5天 -> " + score3);

        Date reportTime4 = new Date(now.getTime() - 6 * 24 * 60 * 60 * 1000L);
        double score4 = mapper.quickSettleOwnToStreet(false, null, now, reportTime4);
        assertEquals(5.0, score4, 0.01); // 20 - 5*(6-3) = 5
        System.out.println("t=6天 -> " + score4);

        Date reportTime5 = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000L);
        double score5 = mapper.quickSettleOwnToStreet(false, null, now, reportTime5);
        assertEquals(0.0, score5, 0.01);
        System.out.println("t=7天 -> " + score5);

        // ----- 有正式续报 -----
        System.out.println("--- 有正式续报 ---");

        Date reportTime6 = new Date(now.getTime() - 20 * 24 * 60 * 60 * 1000L);

        Date lastFollowUp1 = new Date(now.getTime() - 2 * 24 * 60 * 60 * 1000L);
        double score6 = mapper.quickSettleOwnToStreet(true, lastFollowUp1, now, reportTime6);
        assertEquals(20.0, score6, 0.01);
        System.out.println("续报后2天办结 -> " + score6);

        Date lastFollowUp2 = new Date(now.getTime() - 4 * 24 * 60 * 60 * 1000L);
        double score7 = mapper.quickSettleOwnToStreet(true, lastFollowUp2, now, reportTime6);
        assertEquals(15.0, score7, 0.01);
        System.out.println("续报后4天办结 -> " + score7);

        Date lastFollowUp3 = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000L);
        double score8 = mapper.quickSettleOwnToStreet(true, lastFollowUp3, now, reportTime6);
        assertEquals(0.0, score8, 0.01);
        System.out.println("续报后7天办结 -> " + score8);
    }

    /**
     * 测试快速协同回复 - 考核协同目标单位回复时效性
     * A/B类: T=5/10, 上限2T
     * C/D类: T=15/30, 上限4T
     */
    @Test
    public void quickReplyTargetUnit() {
        System.out.println("========== 快速协同回复-考核协同目标单位 测试 ==========");

        // ----- A类事件 (T=5) -----
        System.out.println("--- A类事件 (T=5) ---");

        double score1 = mapper.quickReplyTargetUnit("A", 3.0);
        assertEquals(10.0, score1, 0.01);
        System.out.println("t=3 (<=T) -> " + score1);

        double score2 = mapper.quickReplyTargetUnit("A", 5.0);
        assertEquals(10.0, score2, 0.01);
        System.out.println("t=5 (刚好T) -> " + score2);

        double score3 = mapper.quickReplyTargetUnit("A", 7.0);
        assertEquals(6.0, score3, 0.01); // 10 - (7-5)/5*10 = 6
        System.out.println("t=7 (超时) -> " + score3);

        double score4 = mapper.quickReplyTargetUnit("A", 10.0);
        assertEquals(0.0, score4, 0.01); // 10 - (10-5)/5*10 = 0
        System.out.println("t=10 (刚好2T) -> " + score4);

        double score5 = mapper.quickReplyTargetUnit("A", 11.0);
        assertEquals(0.0, score5, 0.01);
        System.out.println("t=11 (超过2T) -> " + score5);

        // ----- B类事件 (T=10) -----
        System.out.println("--- B类事件 (T=10) ---");

        double score6 = mapper.quickReplyTargetUnit("B", 8.0);
        assertEquals(10.0, score6, 0.01);
        System.out.println("t=8 (<=T) -> " + score6);

        double score7 = mapper.quickReplyTargetUnit("B", 10.0);
        assertEquals(10.0, score7, 0.01);
        System.out.println("t=10 (刚好T) -> " + score7);

        double score8 = mapper.quickReplyTargetUnit("B", 15.0);
        assertEquals(5.0, score8, 0.01); // 10 - (15-10)/10*10 = 5
        System.out.println("t=15 (超时) -> " + score8);

        double score9 = mapper.quickReplyTargetUnit("B", 20.0);
        assertEquals(0.0, score9, 0.01);
        System.out.println("t=20 (刚好2T) -> " + score9);

        // ----- C类事件 (T=15) -----
        System.out.println("--- C类事件 (T=15) ---");

        double score10 = mapper.quickReplyTargetUnit("C", 12.0);
        assertEquals(10.0, score10, 0.01);
        System.out.println("t=12 (<=T) -> " + score10);

        double score11 = mapper.quickReplyTargetUnit("C", 15.0);
        assertEquals(10.0, score11, 0.01);
        System.out.println("t=15 (刚好T) -> " + score11);

        double score12 = mapper.quickReplyTargetUnit("C", 30.0);
        assertEquals(6.7, score12, 0.01); // 10 - (30-15)/(3*15)*10 = 6.67
        System.out.println("t=30 (超时) -> " + score12);

        double score13 = mapper.quickReplyTargetUnit("C", 60.0);
        assertEquals(0.0, score13, 0.01); // 10 - (60-15)/(45)*10 = 0
        System.out.println("t=60 (刚好4T) -> " + score13);

        double score14 = mapper.quickReplyTargetUnit("C", 61.0);
        assertEquals(0.0, score14, 0.01);
        System.out.println("t=61 (超过4T) -> " + score14);

        // ----- D类事件 (T=30) -----
        System.out.println("--- D类事件 (T=30) ---");

        double score15 = mapper.quickReplyTargetUnit("D", 25.0);
        assertEquals(10.0, score15, 0.01);
        System.out.println("t=25 (<=T) -> " + score15);

        double score16 = mapper.quickReplyTargetUnit("D", 30.0);
        assertEquals(10.0, score16, 0.01);
        System.out.println("t=30 (刚好T) -> " + score16);

        double score17 = mapper.quickReplyTargetUnit("D", 60.0);
        assertEquals(6.7, score17, 0.01); // 10 - (60-30)/(90)*10 = 6.67
        System.out.println("t=60 (超时) -> " + score17);

        double score18 = mapper.quickReplyTargetUnit("D", 120.0);
        assertEquals(0.0, score18, 0.01);
        System.out.println("t=120 (刚好4T) -> " + score18);

        // ----- 异常 -----
        System.out.println("--- 异常 ---");
        double score19 = mapper.quickReplyTargetUnit("X", 10.0);
        assertEquals(0.0, score19, 0.01);
        System.out.println("未知类型 -> " + score19);
    }

    /**
     * 测试快速协同回复 - 考核批示下达交办目标单位回复时效性
     * 规则与协同目标单位回复完全一致
     */
    @Test
    public void quickReplyTargetTimeUnit() {
        System.out.println("========== 快速协同回复-考核批示下达交办目标单位 测试 ==========");
        System.out.println("规则与协同目标单位回复完全一致");

        // A类 (T=5)
        double score1 = mapper.quickReplyTargetTimeUnit("A", 5.0);
        assertEquals(10.0, score1, 0.01);
        System.out.println("A类 t=5 (刚好T) -> " + score1);

        double score2 = mapper.quickReplyTargetTimeUnit("A", 8.0);
        assertEquals(4.0, score2, 0.01); // 10 - (8-5)/5*10 = 4
        System.out.println("A类 t=8 (超时) -> " + score2);

        double score3 = mapper.quickReplyTargetTimeUnit("A", 11.0);
        assertEquals(0.0, score3, 0.01);
        System.out.println("A类 t=11 (超过2T) -> " + score3);

        // B类 (T=10)
        double score4 = mapper.quickReplyTargetTimeUnit("B", 10.0);
        assertEquals(10.0, score4, 0.01);
        System.out.println("B类 t=10 (刚好T) -> " + score4);

        double score5 = mapper.quickReplyTargetTimeUnit("B", 15.0);
        assertEquals(5.0, score5, 0.01);
        System.out.println("B类 t=15 (超时) -> " + score5);

        double score6 = mapper.quickReplyTargetTimeUnit("B", 20.0);
        assertEquals(0.0, score6, 0.01);
        System.out.println("B类 t=20 (刚好2T) -> " + score6);

        // C类 (T=15)
        double score7 = mapper.quickReplyTargetTimeUnit("C", 15.0);
        assertEquals(10.0, score7, 0.01);
        System.out.println("C类 t=15 (刚好T) -> " + score7);

        double score8 = mapper.quickReplyTargetTimeUnit("C", 30.0);
        assertEquals(6.7, score8, 0.01);
        System.out.println("C类 t=30 (超时) -> " + score8);

        double score9 = mapper.quickReplyTargetTimeUnit("C", 60.0);
        assertEquals(0.0, score9, 0.01);
        System.out.println("C类 t=60 (刚好4T) -> " + score9);

        // D类 (T=30)
        double score10 = mapper.quickReplyTargetTimeUnit("D", 30.0);
        assertEquals(10.0, score10, 0.01);
        System.out.println("D类 t=30 (刚好T) -> " + score10);

        double score11 = mapper.quickReplyTargetTimeUnit("D", 60.0);
        assertEquals(6.7, score11, 0.01);
        System.out.println("D类 t=60 (超时) -> " + score11);

        double score12 = mapper.quickReplyTargetTimeUnit("D", 120.0);
        assertEquals(0.0, score12, 0.01);
        System.out.println("D类 t=120 (刚好4T) -> " + score12);

        // 异常
        double score13 = mapper.quickReplyTargetTimeUnit("X", 10.0);
        assertEquals(0.0, score13, 0.01);
        System.out.println("未知类型 -> " + score13);
    }


}
