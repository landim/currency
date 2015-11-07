package com.crossover.exchange.model;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.crossover.exchange.App;

/**
 * @author Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class CurrencyRestTest {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
	private CurrencyRepository currencyRepository;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private MockMvc mockMvc;
    
    private List<Currency> currencies;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        currencyRepository.deleteAll();
        
        currencies = new ArrayList<Currency>();
        currencies.add(new Currency("USD", "United States Dollar", 1));
        currencies.add(new Currency("BRL", "Brazilian Real", 3));
        currencies.add(new Currency("BBD", "Barbadian Dollar", 2));
        
        for (Currency currency : currencies) {
			currencyRepository.save(currency);
		}
    }

    @After
    public void tearDown() throws Exception {
//        currencyRepository.deleteAll();
    }

    @Test
    public void getAllCurrencies() throws Exception {
    	List<Currency> expect = currencies;
        mockMvc.perform(get("/currency")
                .contentType(contentType))
		        .andExpect(status().is(200))
                .andExpect(content().json(this.json(expect)));
    }
    
    @Test
    public void filterCurrenciesByNameUsingRegex() throws Exception {
    	List<Currency> expect = new ArrayList<Currency>();
    	expect.add(new Currency("USD", "United States Dollar", 1));
    	expect.add(new Currency("BBD", "Barbadian Dollar", 2));
    	System.out.println(this.json(expect));
        mockMvc.perform(get("/currency?name=dollar")
                .contentType(contentType))
		        .andExpect(status().is(200))
                .andExpect(content().json(this.json(expect)));
    }
    
    @Test
    public void filterCurrenciesWithTwoWordsUsingWhitespace() throws Exception {
    	List<Currency> expect = new ArrayList<Currency>();
    	expect.add(new Currency("BBD", "Barbadian Dollar", 2));
        mockMvc.perform(get("/currency?name=bar dollar")
                .contentType(contentType))
		        .andExpect(status().is(200))
                .andExpect(content().json(this.json(expect)));
    }
    
    @Test
    public void filterCurrenciesWithTwoWordsUsingPlusSign() throws Exception {
    	List<Currency> expect = new ArrayList<Currency>();
    	expect.add(new Currency("BBD", "Barbadian Dollar", 2));
        mockMvc.perform(get("/currency?name=bar+dollar")
                .contentType(contentType))
		        .andExpect(status().is(200))
                .andExpect(content().json(this.json(expect)));
    }
    
    @Test
    public void convertCurrencyToUSD() throws Exception {
    	double expect = 2.5;
        mockMvc.perform(get("/currency/convert?from=BBD&to=USD&amount=5")
                .contentType(contentType))
		        .andExpect(status().is(200))
                .andExpect(content().json(this.json(expect)));
    }
    
    @Test
    public void convertCurrencyFromUSD() throws Exception {
    	double expect = 6;
        mockMvc.perform(get("/currency/convert?from=USD&to=BRL&amount=2")
                .contentType(contentType))
		        .andExpect(status().is(200))
                .andExpect(content().json(this.json(expect)));
    }
    
    @Test
    public void updateCurrencies() throws Exception {
        mockMvc.perform(get("/currency/update")
                .contentType(contentType))
		        .andExpect(status().is(200));
        Currency russianCurrency = currencyRepository.findByCode("RUB");
        Assert.assertNotNull("Database updated and have russian currency", russianCurrency);
        Assert.assertEquals("Database updates", "Russian Ruble", russianCurrency.getName());
    }
    
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }    
}
