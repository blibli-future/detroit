package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.AgentChannel;
import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.request.NewParameterRequest;
import com.blibli.future.detroit.repository.AgentChannelRepository;
import com.blibli.future.detroit.repository.ParameterRepository;
import com.blibli.future.detroit.util.configuration.Converter;
import org.junit.*;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyObject;

public class ParameterServiceTest {

    // The System Under Test (SUT)
    @InjectMocks
    private ParameterService parameterService;

    // Dependency
    @Mock
    private ParameterRepository parameterRepository;
    @Mock
    private AgentChannelRepository agentChannelRepository;
    @Spy
    private Converter modelMapper = new Converter();

    // Test data
    private AgentChannel agentChannel;
    private Parameter parameter1;
    private Parameter parameter2;
    NewParameterRequest singleParameterRequest;

    private static final Logger LOG = LoggerFactory.getLogger(ParameterServiceTest.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        agentChannel = new AgentChannel();
        agentChannel.setName("Channel");
        agentChannel.setId(1L);

        parameter1 = new Parameter();
        parameter1.setActive(true);
        parameter1.setId(1L);
        parameter1.setName("Parameter 1");
        parameter1.setDescription("Description");
        parameter1.setWeight(60f);
        parameter1.setBulkStatus(false);
        parameter1.setAgentChannel(agentChannel);

        parameter2 = new Parameter();
        parameter2.setActive(true);
        parameter2.setId(2L);
        parameter2.setName("Parameter 2");
        parameter2.setDescription("Description");
        parameter2.setWeight(40f);
        parameter2.setBulkStatus(true);
        parameter2.setAgentChannel(agentChannel);

        singleParameterRequest = new NewParameterRequest();
        singleParameterRequest.setActive(true);
        singleParameterRequest.setId(1L);
        singleParameterRequest.setName("Live Monitoring");
        singleParameterRequest.setDescription("Description");
        singleParameterRequest.setWeight(100f);
        singleParameterRequest.setBulkStatus(false);
    }

    @After
    public void tearDown() {
    }

    @Test
    @Ignore
    public void createParameterTest() {
        // Given
        // Default is zero for new param
        singleParameterRequest.setWeight(0f);
        parameter1.setWeight(0f);
        BDDMockito.given(parameterRepository.saveAndFlush(anyObject())).willReturn(parameter1);

        // When
        Parameter result = parameterService.createParameter(singleParameterRequest);

        // Then
        Assert.assertEquals(parameter1, result);
    }

    @Test
    @Ignore
    public void batchUpdateParameterTest() {
        // Given
        List<Parameter> parameterList = Arrays.asList(parameter1, parameter2);
        BDDMockito.given(agentChannelRepository.getOne(agentChannel.getId())).willReturn(agentChannel);
    }

}
