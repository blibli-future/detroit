package com.blibli.future.detroit.configuration;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.enums.ScoreType;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.repository.*;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private  AgentChannelRepository agentChannelRepository;
    private AgentPositionRepository agentPositionRepository;
    private ParameterRepository parameterRepository;
    private CategoryRepository categoryRepository;
    private ReviewRepository reviewRepository;
    private DetailReviewRepository detailReviewRepository;
    private CutOffRepository cutOffRepository;
    private ScoreSummaryRepository scoreSummaryRepository;

    @Autowired
    public DataSeeder(UserRepository userRepository,
                      UserRoleRepository userRoleRepository,
                      AgentChannelRepository agentChannelRepository,
                      AgentPositionRepository agentPositionRepository,
                      ParameterRepository parameterRepository,
                      CategoryRepository categoryRepository,
                      ReviewRepository reviewRepository,
                      DetailReviewRepository detailReviewRepository,
                      CutOffRepository cutOffRepository,
                      ScoreSummaryRepository scoreSummaryRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.agentChannelRepository = agentChannelRepository;
        this.agentPositionRepository = agentPositionRepository;
        this.parameterRepository = parameterRepository;
        this.categoryRepository = categoryRepository;
        this.reviewRepository = reviewRepository;
        this.detailReviewRepository = detailReviewRepository;
        this.cutOffRepository = cutOffRepository;
        this.scoreSummaryRepository = scoreSummaryRepository;
    }


    public void run(ApplicationArguments args) {

        CutOffHistory cutOffHistoryLama = new CutOffHistory();
        cutOffHistoryLama.setBeginCutOff(new LocalDate(2017,1,1));
        cutOffHistoryLama.setEndCutOff(new LocalDate(2017,2,1));
        cutOffHistoryLama = cutOffRepository.saveAndFlush(cutOffHistoryLama);

        CutOffHistory cutOffHistoryLama1 = new CutOffHistory();
        cutOffHistoryLama1.setBeginCutOff(new LocalDate(2017,2,1));
        cutOffHistoryLama1.setEndCutOff(new LocalDate(2017,3,1));
        cutOffHistoryLama1 = cutOffRepository.saveAndFlush(cutOffHistoryLama1);

        CutOffHistory cutOffHistoryLama2 = new CutOffHistory();
        cutOffHistoryLama2.setBeginCutOff(new LocalDate(2017,3,1));
        cutOffHistoryLama2.setEndCutOff(new LocalDate(2017,4,1));
        cutOffHistoryLama2 = cutOffRepository.saveAndFlush(cutOffHistoryLama2);

        CutOffHistory cutOffHistory = new CutOffHistory();
        LocalDate now1 = new LocalDate(2017, 4, 1);
        LocalDate end1 = new LocalDate();
        cutOffHistory.setBeginCutOff(now1);
        cutOffHistory.setEndCutOff(end1);
        cutOffHistory = cutOffRepository.saveAndFlush(cutOffHistory);

        CutOffHistory cutOffHistory1 = new CutOffHistory();
        LocalDate now = new LocalDate();
        cutOffHistory1.setBeginCutOff(now);
//        LocalDate end = new LocalDate(2017, 8, 1);
//        cutOffHistory1.setEndCutOff(end);
        cutOffHistory1 = cutOffRepository.saveAndFlush(cutOffHistory1);

        AgentChannel agentChannel = new AgentChannel();
        agentChannel.setName("Chat");
        agentChannelRepository.save(agentChannel);

        AgentPosition agentPosition = new AgentPosition();
        agentPosition.setName("Inbound");
        agentPositionRepository.save(agentPosition);

        // API KEY = YWdlbnRAZXhhbXBsZS5jb206c2VjcmV0
        User agent = new User();
        agent.setEmail("agent@example.com");
        agent.setNickname("agent");
        agent.setFullname("Agent Nomor 1");
        agent.setGender("Male");
        agent.setPassword("secret");
        agent.setUserType(UserType.AGENT);
        agent.setAgentChannel(agentChannel);
        agent.setAgentPosition(agentPosition);
        agent = userRepository.saveAndFlush(agent);

        UserRole agentRole = new UserRole(
            agent.getEmail(), agent.getUserType().toString());
        userRoleRepository.save(agentRole);

        // API KEY = YWdlbnRAZXhhbXBsZS5jb206c2VjcmV0
        User agent2 = new User();
        agent2.setEmail("agent2@example.com");
        agent2.setNickname("agent2");
        agent2.setFullname("Agent Nomor 2");
        agent2.setGender("Male");
        agent2.setPassword("secret");
        agent2.setUserType(UserType.AGENT);
        agent2.setAgentChannel(agentChannel);
        agent2.setAgentPosition(agentPosition);
        userRepository.save(agent2);

        UserRole agentRole2 = new UserRole(
            agent2.getEmail(), agent2.getUserType().toString());
        userRoleRepository.save(agentRole2);

        // API KEY = cmV2aWV3ZXJAZXhhbXBsZS5jb206c2VjcmV0
        User reviewer = new User();
        reviewer.setEmail("reviewer@example.com");
        reviewer.setNickname("reviewer");
        reviewer.setFullname("Reviewer Nomor 1");
        reviewer.setGender("Male");
        reviewer.setPassword("secret");
        reviewer.setUserType(UserType.REVIEWER);
        reviewer = userRepository.saveAndFlush(reviewer);

        UserRole reviewerRole = new UserRole(
            reviewer.getEmail(), reviewer.getUserType().toString());
        userRoleRepository.save(reviewerRole);

        UserRole reviewerParameter = new UserRole(
            reviewer.getEmail(), "Reviewer_Live_Monitoring");
        userRoleRepository.save(reviewerParameter);

        // API KEY = c3VwZXJhZG1pbkBleGFtcGxlLmNvbTpzZWNyZXQ=
        User superAdmin = new User();
        superAdmin.setEmail("superadmin@example.com");
        superAdmin.setFullname("Super Admin");
        superAdmin.setGender("Male");
        superAdmin.setPassword("secret");
        superAdmin.setUserType(UserType.SUPER_ADMIN);
        superAdmin = userRepository.saveAndFlush(superAdmin);

        UserRole superAdminRole = new UserRole(
            superAdmin.getEmail(), superAdmin.getUserType().toString());
        userRoleRepository.save(superAdminRole);

        UserRole superAdminParameter = new UserRole(
            superAdmin.getEmail(), "SuperAdmin_Live_Monitoring");
        userRoleRepository.save(superAdminParameter);

        Parameter parameter = new Parameter();
        parameter.setAgentPosition(agentPosition);
        parameter.setName("Live Monitoring");
        parameter.setDescription("Live Monitoring Parameter");
        parameter.setWeight(100f);
        parameter = parameterRepository.saveAndFlush(parameter);

        Category category = new Category();
        category.setName("Opening");
        category.setDescription("Opening Category");
        category.setWeight(50f);
        category.setAgentChannel(agentChannel);
        category.setParameter(parameter);
        category = categoryRepository.saveAndFlush(category);

        Category category1 = new Category();
        category1.setName("Solution");
        category1.setDescription("Solution Category");
        category1.setWeight(50f);
        category1.setAgentChannel(agentChannel);
        category.setParameter(parameter);
        category1 = categoryRepository.saveAndFlush(category1);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryList.add(category1);
        parameter.setCategories(categoryList);
        parameter = parameterRepository.save(parameter);

        Review review = new Review();
        review.setCasemgnt("1234");
        review.setInteractionType("Chat");
        review.setCustomerName("User A");
        review.setTlName("TL A");
        review.setParameter(parameter);
        review.setAgent(agent);
        review.setReviewer(reviewer);
        review.setCutOffHistory(cutOffHistory);
        review = reviewRepository.saveAndFlush(review);

        DetailReview detailReview = new DetailReview();
        detailReview.setCategory(category);
        detailReview.setScore(85f);
        detailReview.setNote("Good Opening, keep it up!");
        detailReview.setReview(review);
        detailReviewRepository.save(detailReview);

        DetailReview detailReview1 = new DetailReview();
        detailReview1.setCategory(category1);
        detailReview1.setScore(65f);
        detailReview1.setNote("I think you can give better solution, please think better solution next time");
        detailReview1.setReview(review);
        detailReviewRepository.save(detailReview1);

        List<DetailReview> detailReviews = new ArrayList<>();
        detailReviews.add(detailReview);
        detailReviews.add(detailReview1);

        Float score = 0f;

        for (DetailReview input : detailReviews) {
            score = score + (input.getCategory().getWeight() / 100) * input.getScore();
        }
        review.setScore(score);
        review.setDetailReview(detailReviews);
        review = reviewRepository.saveAndFlush(review);

        Review review1 = new Review();
        review1.setCasemgnt("4321");
        review1.setInteractionType("Chat");
        review1.setCustomerName("User B");
        review1.setTlName("TL A");
        review1.setParameter(parameter);
        review1.setAgent(agent);
        review1.setReviewer(reviewer);
        review1.setCutOffHistory(cutOffHistory1);
        review1 = reviewRepository.saveAndFlush(review1);

        DetailReview detailReview2 = new DetailReview();
        detailReview2.setCategory(category);
        detailReview2.setScore(65f);
        detailReview2.setNote("Keep your opening performance, fix it up, you can do better than this!");
        detailReview2.setReview(review1);
        detailReviewRepository.save(detailReview2);

        DetailReview detailReview3 = new DetailReview();
        detailReview3.setCategory(category1);
        detailReview3.setScore(70f);
        detailReview3.setNote("that's it, keep improving your solution");
        detailReview3.setReview(review1);
        detailReviewRepository.save(detailReview3);

        List<DetailReview> detailReviews2 = new ArrayList<>();
        detailReviews2.add(detailReview2);
        detailReviews2.add(detailReview3);

        Float score1 = 0f;

        for (DetailReview input1 : detailReviews2) {
            score1 = score1 + (input1.getCategory().getWeight() / 100) * input1.getScore();
        }
        review1.setScore(score1);
        review1.setDetailReview(detailReviews2);
        review1 = reviewRepository.saveAndFlush(review1);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        cutOffHistory.setReviews(reviews);
        cutOffRepository.save(cutOffHistory);

        List<Review> reviews1 = new ArrayList<>();
        reviews1.add(review1);
        cutOffHistory1.setReviews(reviews1);
        cutOffRepository.save(cutOffHistory1);

        ScoreSummary scoreSummary = new ScoreSummary();
        scoreSummary.setName("Live Monitoring");
        scoreSummary.setScore(88.5f);
        scoreSummary.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary.setFkId(1l);
        scoreSummary.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary);

        ScoreSummary scoreSummary1 = new ScoreSummary();
        scoreSummary1.setName("Opening");
        scoreSummary1.setScore(80f);
        scoreSummary1.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary1.setFkId(1l);
        scoreSummary1.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary1);

        ScoreSummary scoreSummary2 = new ScoreSummary();
        scoreSummary2.setName("Solution");
        scoreSummary2.setScore(90f);
        scoreSummary2.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary2.setFkId(2l);
        scoreSummary2.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary2);

        ScoreSummary scoreSummary3 = new ScoreSummary();
        scoreSummary3.setName("Live Monitoring");
        scoreSummary3.setScore(82.5f);
        scoreSummary3.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary3.setFkId(1l);
        scoreSummary3.setCutOffHistory(cutOffHistoryLama);
        scoreSummary3.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary3);

        ScoreSummary scoreSummary4 = new ScoreSummary();
        scoreSummary4.setName("Opening");
        scoreSummary4.setScore(83f);
        scoreSummary4.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary4.setFkId(1l);
        scoreSummary4.setCutOffHistory(cutOffHistoryLama);
        scoreSummary4.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary4);

        ScoreSummary scoreSummary5 = new ScoreSummary();
        scoreSummary5.setName("Solution");
        scoreSummary5.setScore(84.7f);
        scoreSummary5.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary5.setFkId(2l);
        scoreSummary5.setCutOffHistory(cutOffHistoryLama);
        scoreSummary5.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary5);


        ScoreSummary scoreSummary6 = new ScoreSummary();
        scoreSummary6.setName("Live Monitoring");
        scoreSummary6.setScore(75f);
        scoreSummary6.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary6.setFkId(1l);
        scoreSummary6.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary6);

        ScoreSummary scoreSummary7 = new ScoreSummary();
        scoreSummary7.setName("Opening");
        scoreSummary7.setScore(75.6f);
        scoreSummary7.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary7.setFkId(1l);
        scoreSummary7.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary7);

        ScoreSummary scoreSummary8 = new ScoreSummary();
        scoreSummary8.setName("Solution");
        scoreSummary8.setScore(80.2f);
        scoreSummary8.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary8.setFkId(2l);
        scoreSummary8.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary8);

        ScoreSummary scoreSummary9 = new ScoreSummary();
        scoreSummary9.setName("Live Monitoring");
        scoreSummary9.setScore(76.2f);
        scoreSummary9.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary9.setFkId(1l);
        scoreSummary9.setCutOffHistory(cutOffHistoryLama1);
        scoreSummary9.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary9);

        ScoreSummary scoreSummary10 = new ScoreSummary();
        scoreSummary10.setName("Opening");
        scoreSummary10.setScore(73f);
        scoreSummary10.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary10.setFkId(1l);
        scoreSummary10.setCutOffHistory(cutOffHistoryLama1);
        scoreSummary10.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary10);

        ScoreSummary scoreSummary11 = new ScoreSummary();
        scoreSummary11.setName("Solution");
        scoreSummary11.setScore(77.3f);
        scoreSummary11.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary11.setFkId(2l);
        scoreSummary11.setCutOffHistory(cutOffHistoryLama1);
        scoreSummary11.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary11);


        ScoreSummary scoreSummary12 = new ScoreSummary();
        scoreSummary12.setName("Live Monitoring");
        scoreSummary12.setScore(92.4f);
        scoreSummary12.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary12.setFkId(1l);
        scoreSummary12.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary12);

        ScoreSummary scoreSummary13 = new ScoreSummary();
        scoreSummary13.setName("Opening");
        scoreSummary13.setScore(95.6f);
        scoreSummary13.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary13.setFkId(1l);
        scoreSummary13.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary13);

        ScoreSummary scoreSummary14 = new ScoreSummary();
        scoreSummary14.setName("Solution");
        scoreSummary14.setScore(90.2f);
        scoreSummary14.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary14.setFkId(2l);
        scoreSummary14.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary14);

        ScoreSummary scoreSummary15 = new ScoreSummary();
        scoreSummary15.setName("Live Monitoring");
        scoreSummary15.setScore(96.2f);
        scoreSummary15.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary15.setFkId(1l);
        scoreSummary15.setCutOffHistory(cutOffHistoryLama2);
        scoreSummary15.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary15);

        ScoreSummary scoreSummary16 = new ScoreSummary();
        scoreSummary16.setName("Opening");
        scoreSummary16.setScore(93f);
        scoreSummary16.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary16.setFkId(1l);
        scoreSummary16.setCutOffHistory(cutOffHistoryLama2);
        scoreSummary16.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary16);

        ScoreSummary scoreSummary17 = new ScoreSummary();
        scoreSummary17.setName("Solution");
        scoreSummary17.setScore(97.3f);
        scoreSummary17.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary17.setFkId(2l);
        scoreSummary17.setCutOffHistory(cutOffHistoryLama2);
        scoreSummary17.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary17);


        ScoreSummary scoreSummary18 = new ScoreSummary();
        scoreSummary18.setName("Live Monitoring");
        scoreSummary18.setScore(62.4f);
        scoreSummary18.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary18.setFkId(1l);
        scoreSummary18.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary18);

        ScoreSummary scoreSummary19 = new ScoreSummary();
        scoreSummary19.setName("Opening");
        scoreSummary19.setScore(65.6f);
        scoreSummary19.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary19.setFkId(1l);
        scoreSummary19.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary19);

        ScoreSummary scoreSummary20 = new ScoreSummary();
        scoreSummary20.setName("Solution");
        scoreSummary20.setScore(70.2f);
        scoreSummary20.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary20.setFkId(2l);
        scoreSummary20.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary20);

        ScoreSummary scoreSummary21 = new ScoreSummary();
        scoreSummary21.setName("Live Monitoring");
        scoreSummary21.setScore(66.2f);
        scoreSummary21.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary21.setFkId(1l);
        scoreSummary21.setCutOffHistory(cutOffHistory);
        scoreSummary21.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary21);

        ScoreSummary scoreSummary22 = new ScoreSummary();
        scoreSummary22.setName("Opening");
        scoreSummary22.setScore(63f);
        scoreSummary22.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary22.setFkId(1l);
        scoreSummary22.setCutOffHistory(cutOffHistory);
        scoreSummary22.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary22);

        ScoreSummary scoreSummary23 = new ScoreSummary();
        scoreSummary23.setName("Solution");
        scoreSummary23.setScore(67.3f);
        scoreSummary23.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary23.setFkId(2l);
        scoreSummary23.setCutOffHistory(cutOffHistory);
        scoreSummary23.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary23);
    }
}
