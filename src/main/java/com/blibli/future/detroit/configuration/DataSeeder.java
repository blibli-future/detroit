package com.blibli.future.detroit.configuration;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.enums.ScoreType;
import com.blibli.future.detroit.model.enums.Gender;
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

        AgentPosition agentPosition = new AgentPosition();
        agentPosition.setName("Inbound");
        agentPosition = agentPositionRepository.saveAndFlush(agentPosition);

        AgentChannel agentChannel = new AgentChannel();
        agentChannel.setName("Chat");
        agentChannel.setAgentPosition(agentPosition);
        agentChannel = agentChannelRepository.saveAndFlush(agentChannel);

        AgentChannel agentChannel1 = new AgentChannel();
        agentChannel1.setName("Call");
        agentChannel1.setAgentPosition(agentPosition);
        agentChannel1 = agentChannelRepository.saveAndFlush(agentChannel1);

        List<AgentChannel> agentChannels = new ArrayList<>();
        agentChannels.add(agentChannel);
        agentChannels.add(agentChannel1);
        agentPosition.setAgentChannels(agentChannels);
        agentPosition = agentPositionRepository.saveAndFlush(agentPosition);

        // API KEY = YWdlbnRAZXhhbXBsZS5jb206c2VjcmV0
        User agent = new User();
        agent.setEmail("agent@example.com");
        agent.setNickname("agent");
        agent.setFullname("Agent Nomor 1");
        agent.setGender(Gender.PRIA);
        agent.setPassword("$2a$12$igSBCxqY8EDkZnt5w95bvu77996NpvBzTdF4QIgjW8RmwZCthCWlm");
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
        agent2.setGender(Gender.PRIA);
        agent2.setPassword("$2a$12$igSBCxqY8EDkZnt5w95bvu77996NpvBzTdF4QIgjW8RmwZCthCWlm");
        agent2.setUserType(UserType.AGENT);
        agent2.setAgentChannel(agentChannel1);
        agent2.setAgentPosition(agentPosition);
        userRepository.save(agent2);

        List<User> agents = new ArrayList<>();
        agents.add(agent);
        agentChannel.setUsers(agents);
        agentChannel = agentChannelRepository.saveAndFlush(agentChannel);
        agents = new ArrayList<>();
        agents.add(agent2);
        agentChannel1.setUsers(agents);
        agentChannel1 = agentChannelRepository.saveAndFlush(agentChannel1);

        UserRole agentRole2 = new UserRole(
            agent2.getEmail(), agent2.getUserType().toString());
        userRoleRepository.save(agentRole2);

        // API KEY = cmV2aWV3ZXJAZXhhbXBsZS5jb206c2VjcmV0
        User reviewer = new User();
        reviewer.setEmail("reviewer@example.com");
        reviewer.setNickname("reviewer");
        reviewer.setFullname("Reviewer Nomor 1");
        reviewer.setGender(Gender.PRIA);
        reviewer.setPassword("$2a$12$igSBCxqY8EDkZnt5w95bvu77996NpvBzTdF4QIgjW8RmwZCthCWlm");
        reviewer.setUserType(UserType.REVIEWER);
        reviewer = userRepository.saveAndFlush(reviewer);

        UserRole reviewerRole = new UserRole(
            reviewer.getEmail(), reviewer.getUserType().toString());
        userRoleRepository.save(reviewerRole);

        UserRole reviewerParameter = new UserRole(
            reviewer.getEmail(), "PARAM Live Monitoring");
        userRoleRepository.save(reviewerParameter);

//        UserRole reviewerParameter1 = new UserRole(
//            reviewer.getEmail(), "PARAM Best Conversation");
//        userRoleRepository.save(reviewerParameter1);

        UserRole reviewerParameter2 = new UserRole(
            reviewer.getEmail(), "PARAM Absence");
        userRoleRepository.save(reviewerParameter2);

        // API KEY = c3VwZXJhZG1pbkBleGFtcGxlLmNvbTpzZWNyZXQ=
        User superAdmin = new User();
        superAdmin.setEmail("superadmin@example.com");
        superAdmin.setFullname("Super Admin");
        superAdmin.setGender(Gender.PRIA);
        superAdmin.setPassword("$2a$12$igSBCxqY8EDkZnt5w95bvu77996NpvBzTdF4QIgjW8RmwZCthCWlm");
        superAdmin.setUserType(UserType.SUPER_ADMIN);
        superAdmin = userRepository.saveAndFlush(superAdmin);

        UserRole superAdminRole = new UserRole(
            superAdmin.getEmail(), superAdmin.getUserType().toString());
        userRoleRepository.save(superAdminRole);

        UserRole superAdminParameter = new UserRole(
            superAdmin.getEmail(), "PARAM Live Monitoring");
        userRoleRepository.save(superAdminParameter);

        Parameter parameter = new Parameter();
        parameter.setAgentChannel(agentChannel);
        parameter.setName("Live Monitoring");
        parameter.setDescription("Live Monitoring Parameter");
        parameter.setWeight(50f);
        parameter = parameterRepository.saveAndFlush(parameter);

        Parameter parameter1 = new Parameter();
        parameter1.setAgentChannel(agentChannel1);
        parameter1.setName("Best Conversation");
        parameter1.setDescription("Best Conversation Parameter");
        parameter1.setWeight(50f);
        parameter1 = parameterRepository.saveAndFlush(parameter1);

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        agentChannel.setParameters(parameters);
        agentChannel = agentChannelRepository.saveAndFlush(agentChannel);
//        parameters = new ArrayList<>();
//        parameters.add(parameter1);
//        agentChannel1.setParameters(parameters);
//        agentChannel1 = agentChannelRepository.saveAndFlush(agentChannel1);

        Category category = new Category();
        category.setName("Opening");
        category.setDescription("Opening Category");
        category.setWeight(50f);
        category.setParameter(parameter);
        category = categoryRepository.saveAndFlush(category);

        Category category1 = new Category();
        category1.setName("Solution");
        category1.setDescription("Solution Category");
        category1.setWeight(50f);
        category1.setParameter(parameter);
        category1 = categoryRepository.saveAndFlush(category1);

        Category category2 = new Category();
        category2.setName("Opening");
        category2.setDescription("Opening Category");
        category2.setWeight(50f);
        category2.setParameter(parameter1);
        category2 = categoryRepository.saveAndFlush(category2);

        Category category3 = new Category();
        category3.setName("Solution");
        category3.setDescription("Solution Category");
        category3.setWeight(50f);
        category3.setParameter(parameter1);
        category3 = categoryRepository.saveAndFlush(category3);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryList.add(category1);
        parameter.setCategories(categoryList);
        parameter = parameterRepository.saveAndFlush(parameter);

        List<Category> categoryList1 = new ArrayList<>();
        categoryList1.add(category2);
        categoryList1.add(category3);
        parameter1.setCategories(categoryList1);
        parameter1 = parameterRepository.saveAndFlush(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setAgentChannel(agentChannel);
        parameter2.setName("Absence");
        parameter2.setDescription("Agent's Absence");
        parameter2.setWeight(50f);
        parameter2.setBulkStatus(true);
        parameter2 = parameterRepository.saveAndFlush(parameter2);

        Category category4 = new Category();
        category4.setName("Absence");
        category4.setDescription("Agent's Absence");
        category4.setWeight(100f);
        category4.setParameter(parameter2);
        category4 = categoryRepository.saveAndFlush(category4);

        Parameter parameter3 = new Parameter();
        parameter3.setAgentChannel(agentChannel1);
        parameter3.setName("Absence");
        parameter3.setDescription("Agent's Absence");
        parameter3.setWeight(50f);
        parameter3.setBulkStatus(true);
        parameter3 = parameterRepository.saveAndFlush(parameter3);

        Category category5 = new Category();
        category5.setName("Absence");
        category5.setDescription("Agent's Absence");
        category5.setWeight(100f);
        category5.setParameter(parameter3);
        category5 = categoryRepository.saveAndFlush(category5);

        // START Give specific role to reviewer

//        userRoleRepository.save(new UserRole(reviewer, parameter));
//        userRoleRepository.save(new UserRole(reviewer, parameter1));

        // END Give specific role to reviewer

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
        detailReview = detailReviewRepository.saveAndFlush(detailReview);

        DetailReview detailReview1 = new DetailReview();
        detailReview1.setCategory(category1);
        detailReview1.setScore(65f);
        detailReview1.setNote("I think you can give better solution, please think better solution next time");
        detailReview1.setReview(review);
        detailReview1 = detailReviewRepository.saveAndFlush(detailReview1);

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
        detailReview2 = detailReviewRepository.saveAndFlush(detailReview2);

        DetailReview detailReview3 = new DetailReview();
        detailReview3.setCategory(category1);
        detailReview3.setScore(70f);
        detailReview3.setNote("that's it, keep improving your solution");
        detailReview3.setReview(review1);
        detailReview3 = detailReviewRepository.saveAndFlush(detailReview3);

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

        Review review2 = new Review();
        review2.setCasemgnt("5678");
        review2.setInteractionType("Call");
        review2.setCustomerName("User C");
        review2.setTlName("TL B");
        review2.setParameter(parameter1);
        review2.setAgent(agent2);
        review2.setReviewer(reviewer);
        review2.setCutOffHistory(cutOffHistory1);
        review2 = reviewRepository.saveAndFlush(review2);

        DetailReview detailReview4 = new DetailReview();
        detailReview4.setCategory(category2);
        detailReview4.setScore(85f);
        detailReview4.setNote("Your opening is great!");
        detailReview4.setReview(review2);
        detailReviewRepository.save(detailReview4);

        DetailReview detailReview5 = new DetailReview();
        detailReview5.setCategory(category3);
        detailReview5.setScore(90f);
        detailReview5.setNote("Great solution for great customer");
        detailReview5.setReview(review2);
        detailReviewRepository.save(detailReview5);

        List<DetailReview> detailReviews3 = new ArrayList<>();
        detailReviews3.add(detailReview4);
        detailReviews3.add(detailReview5);

        Float score2 = 0f;

        for (DetailReview input2 : detailReviews3) {
            score2 = score2 + (input2.getCategory().getWeight() / 100) * input2.getScore();
        }
        review2.setScore(score2);
        review2.setDetailReview(detailReviews3);
        review2 = reviewRepository.saveAndFlush(review2);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        cutOffHistory.setReviews(reviews);
        cutOffRepository.save(cutOffHistory);

        List<Review> reviews1 = new ArrayList<>();
        reviews1.add(review1);
        cutOffHistory1.setReviews(reviews1);
        cutOffRepository.save(cutOffHistory1);

        List<Review> reviews2 = new ArrayList<>();
        reviews2.add(review2);
        cutOffHistory1.setReviews(reviews2);
        cutOffRepository.save(cutOffHistory1);

        ScoreSummary scoreSummary52 = new ScoreSummary();
        scoreSummary52.setName(agentChannel.getName());
        scoreSummary52.setScore(74.9f);
        scoreSummary52.setScoreType(ScoreType.TOTAL_CHANNEL);
        scoreSummary52.setFkId(agentChannel.getId());
        scoreSummary52.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary52);

        ScoreSummary scoreSummary56 = new ScoreSummary();
        scoreSummary56.setName(agentChannel1.getName());
        scoreSummary56.setScore(73.1f);
        scoreSummary56.setScoreType(ScoreType.TOTAL_CHANNEL);
        scoreSummary56.setFkId(agentChannel1.getId());
        scoreSummary56.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary56);

        ScoreSummary scoreSummary48 = new ScoreSummary();
        scoreSummary48.setName("Total Review");
        scoreSummary48.setScore(82.5f);
        scoreSummary48.setScoreType(ScoreType.TOTAL_REVIEW);
        scoreSummary48.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary48);

        ScoreSummary scoreSummary = new ScoreSummary();
        scoreSummary.setName("Live Monitoring");
        scoreSummary.setScore(88.5f);
        scoreSummary.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary.setFkId(parameter.getId());
        scoreSummary.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary);

        ScoreSummary scoreSummary1 = new ScoreSummary();
        scoreSummary1.setName("Opening");
        scoreSummary1.setScore(80f);
        scoreSummary1.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary1.setFkId(category.getId());
        scoreSummary1.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary1);

        ScoreSummary scoreSummary2 = new ScoreSummary();
        scoreSummary2.setName("Solution");
        scoreSummary2.setScore(90f);
        scoreSummary2.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary2.setFkId(category1.getId());
        scoreSummary2.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary2);

        ScoreSummary scoreSummary24 = new ScoreSummary();
        scoreSummary24.setName("Best Conversation");
        scoreSummary24.setScore(71.2f);
        scoreSummary24.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary24.setFkId(parameter1.getId());
        scoreSummary24.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary24);

        ScoreSummary scoreSummary25 = new ScoreSummary();
        scoreSummary25.setName("Opening");
        scoreSummary25.setScore(68f);
        scoreSummary25.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary25.setFkId(category2.getId());
        scoreSummary25.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary25);

        ScoreSummary scoreSummary26 = new ScoreSummary();
        scoreSummary26.setName("Solution");
        scoreSummary26.setScore(70f);
        scoreSummary26.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary26.setFkId(category3.getId());
        scoreSummary26.setCutOffHistory(cutOffHistoryLama);
        scoreSummaryRepository.save(scoreSummary26);

        ScoreSummary scoreSummary60 = new ScoreSummary();
        scoreSummary60.setName("User Total");
        scoreSummary60.setScore(77.1f);
        scoreSummary60.setScoreType(ScoreType.USER_TOTAL);
        scoreSummary60.setCutOffHistory(cutOffHistoryLama);
        scoreSummary60.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary60);

        ScoreSummary scoreSummary3 = new ScoreSummary();
        scoreSummary3.setName("Live Monitoring");
        scoreSummary3.setScore(82.5f);
        scoreSummary3.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary3.setFkId(parameter.getId());
        scoreSummary3.setCutOffHistory(cutOffHistoryLama);
        scoreSummary3.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary3);

        ScoreSummary scoreSummary4 = new ScoreSummary();
        scoreSummary4.setName("Opening");
        scoreSummary4.setScore(83f);
        scoreSummary4.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary4.setFkId(category.getId());
        scoreSummary4.setCutOffHistory(cutOffHistoryLama);
        scoreSummary4.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary4);

        ScoreSummary scoreSummary5 = new ScoreSummary();
        scoreSummary5.setName("Solution");
        scoreSummary5.setScore(84.7f);
        scoreSummary5.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary5.setFkId(category1.getId());
        scoreSummary5.setCutOffHistory(cutOffHistoryLama);
        scoreSummary5.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary5);

        ScoreSummary scoreSummary27 = new ScoreSummary();
        scoreSummary27.setName("Best Conversation");
        scoreSummary27.setScore(72.5f);
        scoreSummary27.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary27.setFkId(parameter1.getId());
        scoreSummary27.setCutOffHistory(cutOffHistoryLama);
        scoreSummary27.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary27);

        ScoreSummary scoreSummary28 = new ScoreSummary();
        scoreSummary28.setName("Opening");
        scoreSummary28.setScore(62.3f);
        scoreSummary28.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary28.setFkId(category2.getId());
        scoreSummary28.setCutOffHistory(cutOffHistoryLama);
        scoreSummary28.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary28);

        ScoreSummary scoreSummary29 = new ScoreSummary();
        scoreSummary29.setName("Solution");
        scoreSummary29.setScore(67.4f);
        scoreSummary29.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary29.setFkId(category3.getId());
        scoreSummary29.setCutOffHistory(cutOffHistoryLama);
        scoreSummary29.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary29);


        ScoreSummary scoreSummary53 = new ScoreSummary();
        scoreSummary53.setName(agentChannel.getName());
        scoreSummary53.setScore(87.3f);
        scoreSummary53.setScoreType(ScoreType.TOTAL_CHANNEL);
        scoreSummary53.setFkId(agentChannel.getId());
        scoreSummary53.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary53);

        ScoreSummary scoreSummary57 = new ScoreSummary();
        scoreSummary57.setName(agentChannel1.getName());
        scoreSummary57.setScore(86.7f);
        scoreSummary57.setScoreType(ScoreType.TOTAL_CHANNEL);
        scoreSummary57.setFkId(agentChannel1.getId());
        scoreSummary57.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary57);

        ScoreSummary scoreSummary49 = new ScoreSummary();
        scoreSummary49.setName("Total Review");
        scoreSummary49.setScore(72.5f);
        scoreSummary49.setScoreType(ScoreType.TOTAL_REVIEW);
        scoreSummary49.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary49);

        ScoreSummary scoreSummary6 = new ScoreSummary();
        scoreSummary6.setName("Live Monitoring");
        scoreSummary6.setScore(75f);
        scoreSummary6.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary6.setFkId(parameter.getId());
        scoreSummary6.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary6);

        ScoreSummary scoreSummary7 = new ScoreSummary();
        scoreSummary7.setName("Opening");
        scoreSummary7.setScore(75.6f);
        scoreSummary7.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary7.setFkId(category.getId());
        scoreSummary7.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary7);

        ScoreSummary scoreSummary8 = new ScoreSummary();
        scoreSummary8.setName("Solution");
        scoreSummary8.setScore(80.2f);
        scoreSummary8.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary8.setFkId(category1.getId());
        scoreSummary8.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary8);

        ScoreSummary scoreSummary30 = new ScoreSummary();
        scoreSummary30.setName("Best Conversation");
        scoreSummary30.setScore(85.2f);
        scoreSummary30.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary30.setFkId(parameter1.getId());
        scoreSummary30.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary30);

        ScoreSummary scoreSummary31 = new ScoreSummary();
        scoreSummary31.setName("Opening");
        scoreSummary31.setScore(95.6f);
        scoreSummary31.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary31.setFkId(category2.getId());
        scoreSummary31.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary31);

        ScoreSummary scoreSummary32 = new ScoreSummary();
        scoreSummary32.setName("Solution");
        scoreSummary32.setScore(62.1f);
        scoreSummary32.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary32.setFkId(category3.getId());
        scoreSummary32.setCutOffHistory(cutOffHistoryLama1);
        scoreSummaryRepository.save(scoreSummary32);

        ScoreSummary scoreSummary61 = new ScoreSummary();
        scoreSummary61.setName("User Total");
        scoreSummary61.setScore(96.3f);
        scoreSummary61.setScoreType(ScoreType.USER_TOTAL);
        scoreSummary61.setCutOffHistory(cutOffHistoryLama1);
        scoreSummary61.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary61);

        ScoreSummary scoreSummary9 = new ScoreSummary();
        scoreSummary9.setName("Live Monitoring");
        scoreSummary9.setScore(76.2f);
        scoreSummary9.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary9.setFkId(parameter.getId());
        scoreSummary9.setCutOffHistory(cutOffHistoryLama1);
        scoreSummary9.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary9);

        ScoreSummary scoreSummary10 = new ScoreSummary();
        scoreSummary10.setName("Opening");
        scoreSummary10.setScore(73f);
        scoreSummary10.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary10.setFkId(category.getId());
        scoreSummary10.setCutOffHistory(cutOffHistoryLama1);
        scoreSummary10.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary10);

        ScoreSummary scoreSummary11 = new ScoreSummary();
        scoreSummary11.setName("Solution");
        scoreSummary11.setScore(77.3f);
        scoreSummary11.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary11.setFkId(category1.getId());
        scoreSummary11.setCutOffHistory(cutOffHistoryLama1);
        scoreSummary11.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary11);

        ScoreSummary scoreSummary33 = new ScoreSummary();
        scoreSummary33.setName("Best Conversation");
        scoreSummary33.setScore(62.6f);
        scoreSummary33.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary33.setFkId(parameter1.getId());
        scoreSummary33.setCutOffHistory(cutOffHistoryLama1);
        scoreSummary33.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary33);

        ScoreSummary scoreSummary34 = new ScoreSummary();
        scoreSummary34.setName("Opening");
        scoreSummary34.setScore(53.2f);
        scoreSummary34.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary34.setFkId(category2.getId());
        scoreSummary34.setCutOffHistory(cutOffHistoryLama1);
        scoreSummary34.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary34);

        ScoreSummary scoreSummary35 = new ScoreSummary();
        scoreSummary35.setName("Solution");
        scoreSummary35.setScore(56.7f);
        scoreSummary35.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary35.setFkId(category3.getId());
        scoreSummary35.setCutOffHistory(cutOffHistoryLama1);
        scoreSummary35.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary35);


        ScoreSummary scoreSummary54 = new ScoreSummary();
        scoreSummary54.setName(agentChannel.getName());
        scoreSummary54.setScore(79.1f);
        scoreSummary54.setScoreType(ScoreType.TOTAL_CHANNEL);
        scoreSummary54.setFkId(agentChannel.getId());
        scoreSummary54.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary54);

        ScoreSummary scoreSummary58 = new ScoreSummary();
        scoreSummary58.setName(agentChannel1.getName());
        scoreSummary58.setScore(88.1f);
        scoreSummary58.setScoreType(ScoreType.TOTAL_CHANNEL);
        scoreSummary58.setFkId(agentChannel1.getId());
        scoreSummary58.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary58);

        ScoreSummary scoreSummary50 = new ScoreSummary();
        scoreSummary50.setName("Total Review");
        scoreSummary50.setScore(77.9f);
        scoreSummary50.setScoreType(ScoreType.TOTAL_REVIEW);
        scoreSummary50.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary50);

        ScoreSummary scoreSummary12 = new ScoreSummary();
        scoreSummary12.setName("Live Monitoring");
        scoreSummary12.setScore(92.4f);
        scoreSummary12.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary12.setFkId(parameter.getId());
        scoreSummary12.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary12);

        ScoreSummary scoreSummary13 = new ScoreSummary();
        scoreSummary13.setName("Opening");
        scoreSummary13.setScore(95.6f);
        scoreSummary13.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary13.setFkId(category.getId());
        scoreSummary13.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary13);

        ScoreSummary scoreSummary14 = new ScoreSummary();
        scoreSummary14.setName("Solution");
        scoreSummary14.setScore(90.2f);
        scoreSummary14.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary14.setFkId(category1.getId());
        scoreSummary14.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary14);

        ScoreSummary scoreSummary36 = new ScoreSummary();
        scoreSummary36.setName("Best Conversation");
        scoreSummary36.setScore(84.8f);
        scoreSummary36.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary36.setFkId(parameter1.getId());
        scoreSummary36.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary36);

        ScoreSummary scoreSummary37 = new ScoreSummary();
        scoreSummary37.setName("Opening");
        scoreSummary37.setScore(82.3f);
        scoreSummary37.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary37.setFkId(category2.getId());
        scoreSummary37.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary37);

        ScoreSummary scoreSummary38 = new ScoreSummary();
        scoreSummary38.setName("Solution");
        scoreSummary38.setScore(72.9f);
        scoreSummary38.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary38.setFkId(category3.getId());
        scoreSummary38.setCutOffHistory(cutOffHistoryLama2);
        scoreSummaryRepository.save(scoreSummary38);

        ScoreSummary scoreSummary62 = new ScoreSummary();
        scoreSummary62.setName("User Total");
        scoreSummary62.setScore(87.3f);
        scoreSummary62.setScoreType(ScoreType.USER_TOTAL);
        scoreSummary62.setCutOffHistory(cutOffHistoryLama2);
        scoreSummary62.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary62);

        ScoreSummary scoreSummary15 = new ScoreSummary();
        scoreSummary15.setName("Live Monitoring");
        scoreSummary15.setScore(96.2f);
        scoreSummary15.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary15.setFkId(parameter.getId());
        scoreSummary15.setCutOffHistory(cutOffHistoryLama2);
        scoreSummary15.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary15);

        ScoreSummary scoreSummary16 = new ScoreSummary();
        scoreSummary16.setName("Opening");
        scoreSummary16.setScore(93f);
        scoreSummary16.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary16.setFkId(category.getId());
        scoreSummary16.setCutOffHistory(cutOffHistoryLama2);
        scoreSummary16.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary16);

        ScoreSummary scoreSummary17 = new ScoreSummary();
        scoreSummary17.setName("Solution");
        scoreSummary17.setScore(97.3f);
        scoreSummary17.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary17.setFkId(category1.getId());
        scoreSummary17.setCutOffHistory(cutOffHistoryLama2);
        scoreSummary17.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary17);

        ScoreSummary scoreSummary39 = new ScoreSummary();
        scoreSummary39.setName("Best Conversation");
        scoreSummary39.setScore(86.2f);
        scoreSummary39.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary39.setFkId(parameter1.getId());
        scoreSummary39.setCutOffHistory(cutOffHistoryLama2);
        scoreSummary39.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary39);

        ScoreSummary scoreSummary40 = new ScoreSummary();
        scoreSummary40.setName("Opening");
        scoreSummary40.setScore(83f);
        scoreSummary40.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary40.setFkId(category2.getId());
        scoreSummary40.setCutOffHistory(cutOffHistoryLama2);
        scoreSummary40.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary40);

        ScoreSummary scoreSummary41 = new ScoreSummary();
        scoreSummary41.setName("Solution");
        scoreSummary41.setScore(77.3f);
        scoreSummary41.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary41.setFkId(category3.getId());
        scoreSummary41.setCutOffHistory(cutOffHistoryLama2);
        scoreSummary41.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary41);


        ScoreSummary scoreSummary55 = new ScoreSummary();
        scoreSummary55.setName(agentChannel.getName());
        scoreSummary55.setScore(79.1f);
        scoreSummary55.setScoreType(ScoreType.TOTAL_CHANNEL);
        scoreSummary55.setFkId(agentChannel.getId());
        scoreSummary55.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary55);

        ScoreSummary scoreSummary59 = new ScoreSummary();
        scoreSummary59.setName(agentChannel1.getName());
        scoreSummary59.setScore(91.7f);
        scoreSummary59.setScoreType(ScoreType.TOTAL_CHANNEL);
        scoreSummary59.setFkId(agentChannel1.getId());
        scoreSummary59.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary59);

        ScoreSummary scoreSummary51 = new ScoreSummary();
        scoreSummary51.setName("Total Review");
        scoreSummary51.setScore(85.7f);
        scoreSummary51.setScoreType(ScoreType.TOTAL_REVIEW);
        scoreSummary51.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary51);

        ScoreSummary scoreSummary18 = new ScoreSummary();
        scoreSummary18.setName("Live Monitoring");
        scoreSummary18.setScore(62.4f);
        scoreSummary18.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary18.setFkId(parameter.getId());
        scoreSummary18.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary18);

        ScoreSummary scoreSummary19 = new ScoreSummary();
        scoreSummary19.setName("Opening");
        scoreSummary19.setScore(65.6f);
        scoreSummary19.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary19.setFkId(category.getId());
        scoreSummary19.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary19);

        ScoreSummary scoreSummary20 = new ScoreSummary();
        scoreSummary20.setName("Solution");
        scoreSummary20.setScore(70.2f);
        scoreSummary20.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary20.setFkId(category1.getId());
        scoreSummary20.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary20);

        ScoreSummary scoreSummary42 = new ScoreSummary();
        scoreSummary42.setName("Best Conversation");
        scoreSummary42.setScore(74.2f);
        scoreSummary42.setScoreType(ScoreType.ALL_PARAMETER);
        scoreSummary42.setFkId(parameter1.getId());
        scoreSummary42.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary42);

        ScoreSummary scoreSummary43 = new ScoreSummary();
        scoreSummary43.setName("Opening");
        scoreSummary43.setScore(75.6f);
        scoreSummary43.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary43.setFkId(category2.getId());
        scoreSummary43.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary43);

        ScoreSummary scoreSummary44 = new ScoreSummary();
        scoreSummary44.setName("Solution");
        scoreSummary44.setScore(80.2f);
        scoreSummary44.setScoreType(ScoreType.ALL_CATEGORY);
        scoreSummary44.setFkId(category3.getId());
        scoreSummary44.setCutOffHistory(cutOffHistory);
        scoreSummaryRepository.save(scoreSummary44);

        ScoreSummary scoreSummary63 = new ScoreSummary();
        scoreSummary63.setName("User Total");
        scoreSummary63.setScore(66.9f);
        scoreSummary63.setScoreType(ScoreType.USER_TOTAL);
        scoreSummary63.setCutOffHistory(cutOffHistory);
        scoreSummary63.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary63);

        ScoreSummary scoreSummary21 = new ScoreSummary();
        scoreSummary21.setName("Live Monitoring");
        scoreSummary21.setScore(66.2f);
        scoreSummary21.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary21.setFkId(parameter.getId());
        scoreSummary21.setCutOffHistory(cutOffHistory);
        scoreSummary21.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary21);

        ScoreSummary scoreSummary22 = new ScoreSummary();
        scoreSummary22.setName("Opening");
        scoreSummary22.setScore(63f);
        scoreSummary22.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary22.setFkId(category.getId());
        scoreSummary22.setCutOffHistory(cutOffHistory);
        scoreSummary22.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary22);

        ScoreSummary scoreSummary23 = new ScoreSummary();
        scoreSummary23.setName("Solution");
        scoreSummary23.setScore(67.3f);
        scoreSummary23.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary23.setFkId(category1.getId());
        scoreSummary23.setCutOffHistory(cutOffHistory);
        scoreSummary23.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary23);

        ScoreSummary scoreSummary45 = new ScoreSummary();
        scoreSummary45.setName("Best Conversation");
        scoreSummary45.setScore(86.2f);
        scoreSummary45.setScoreType(ScoreType.USER_PARAMETER);
        scoreSummary45.setFkId(parameter1.getId());
        scoreSummary45.setCutOffHistory(cutOffHistory);
        scoreSummary45.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary45);

        ScoreSummary scoreSummary46 = new ScoreSummary();
        scoreSummary46.setName("Opening");
        scoreSummary46.setScore(83.5f);
        scoreSummary46.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary46.setFkId(category2.getId());
        scoreSummary46.setCutOffHistory(cutOffHistory);
        scoreSummary46.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary46);

        ScoreSummary scoreSummary47 = new ScoreSummary();
        scoreSummary47.setName("Solution");
        scoreSummary47.setScore(87.3f);
        scoreSummary47.setScoreType(ScoreType.USER_CATEGORY);
        scoreSummary47.setFkId(category3.getId());
        scoreSummary47.setCutOffHistory(cutOffHistory);
        scoreSummary47.setAgent(agent);
        scoreSummaryRepository.save(scoreSummary47);
    }
}
