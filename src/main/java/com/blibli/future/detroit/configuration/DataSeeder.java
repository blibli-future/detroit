package com.blibli.future.detroit.configuration;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.repository.*;
import org.apache.tomcat.jni.Local;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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

    @Autowired
    public DataSeeder(UserRepository userRepository,
                      UserRoleRepository userRoleRepository,
                      AgentChannelRepository agentChannelRepository,
                      AgentPositionRepository agentPositionRepository,
                      ParameterRepository parameterRepository,
                      CategoryRepository categoryRepository,
                      ReviewRepository reviewRepository,
                      DetailReviewRepository detailReviewRepository,
                      CutOffRepository cutOffRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.agentChannelRepository = agentChannelRepository;
        this.agentPositionRepository = agentPositionRepository;
        this.parameterRepository = parameterRepository;
        this.categoryRepository = categoryRepository;
        this.reviewRepository = reviewRepository;
        this.detailReviewRepository = detailReviewRepository;
        this.cutOffRepository = cutOffRepository;
    }


    public void run(ApplicationArguments args) {
        CutOffHistory cutOffHistory = new CutOffHistory();
        LocalDate now1 = new LocalDate(2017, 4, 1);
        LocalDate end1 = new LocalDate(2017, 4, 30);
        cutOffHistory.setBegin(now1);
        cutOffHistory.setEnd(end1);
        cutOffHistory = cutOffRepository.saveAndFlush(cutOffHistory);

        CutOffHistory cutOffHistory1 = new CutOffHistory();
        LocalDate now = new LocalDate();
        cutOffHistory1.setBegin(now);
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
        parameter.setWeight(50f);
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
        detailReview1.setScore(50f);
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
    }
}
