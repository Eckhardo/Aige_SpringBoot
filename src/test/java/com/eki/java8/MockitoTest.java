package com.eki.java8;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.data.domain.Example;

import com.eki.shipment.dao.GeoScopeRepository;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.service.GeoScopeService;

/**
 * Mockito tests for<ul>
 * <li> {@link org.mockito.stubbing.OngoingStubbing#thenReturn(Object)} </li>
 * <li> {@link org.mockito.stubbing.OngoingStubbing#thenAnswer(org.mockito.stubbing.Answer)}  </li>
 * <li> {@link org.mockito.ArgumentMatchers#argThat(org.mockito.ArgumentMatcher)}</li>
 * <li> {@link org.mockito.ArgumentCaptor)}</li>
 *  
 *  that use Java 8 features like Lambda expressions and {@link java.util.Optional}.
 * 
 * 
 * 
 * 
 * @author ekirschning
 *
 */
public class MockitoTest {


	@Mock
	private GeoScopeRepository geoScopeRepository;
	
	@Captor
	private ArgumentCaptor <Example<GeoScope>> geoScopeCaptor;

	@InjectMocks
	private GeoScopeService geoScopeService;

	GeoScope exampleGeoScope;

	GeoScope exampleGeoScope2;
	

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		geoScopeService = new GeoScopeService();
		geoScopeService.setGeoScopeRepository(geoScopeRepository);
		exampleGeoScope = new GeoScope(1L, "DE", "DEHAM", "L", "Hamburg", false);
		exampleGeoScope2 = new GeoScope(2L, "UY", "UYMVD", "L", "Montevideo", false);

	}

	@Test
	public void shouldReturnOptionalGeoScope_whenThenReturn() throws Exception {
		// Arrange
		when(geoScopeRepository.findById(5L)).thenReturn(Optional.of(exampleGeoScope));
		// Act
		Optional<GeoScope> retrievedGeoScope = geoScopeService.findOne(5L);
		Optional<GeoScope> emptyGeoScope = geoScopeService.findOne(1L);

		// Assert
		assertThat(retrievedGeoScope, equalTo(Optional.of(exampleGeoScope)));
		assertThat(emptyGeoScope, equalTo(Optional.empty()));
	}

	@Test
	public void shouldReturnOptionalGeoScope_whenArgumentMatcher() throws Exception {
		// Arrange
		when(geoScopeRepository
				.findByLocationName(ArgumentMatchers.argThat((locationName) -> locationName.equals("Montevideo"))))
						.thenReturn(Optional.of(exampleGeoScope2));

		// Act
		Optional<GeoScope> retrievedGeoScope = geoScopeService.findByLocationName("Montevideo");
		Optional<GeoScope> emptyGeoScope = geoScopeService.findByLocationName("Hamburg");

		// Assert
		assertThat(retrievedGeoScope, equalTo(Optional.of(exampleGeoScope2)));
		assertThat(emptyGeoScope, equalTo(Optional.empty()));
	
	}

	@Test
	public void shouldReturnOptionalGeoScope_whenThenAnswer() throws Exception {
		// Arrange
		when(geoScopeRepository.findByLocationName(Mockito.anyString())).thenAnswer((InvocationOnMock invocation) -> {
			String locationName = (String) invocation.getArguments()[0];
			System.out.println(locationName);
			if (locationName.equals("Montevideo")) {
				return Optional.of(exampleGeoScope2);
			} else {
				return Optional.empty();
			}

		});
		// Act
		Optional<GeoScope> retrievedGeoScope = geoScopeService.findByLocationName("Montevideo");
		Optional<GeoScope> emptyGeoScope = geoScopeService.findByLocationName("Hamburg");

		// Assert
		assertThat(retrievedGeoScope, equalTo(Optional.of(exampleGeoScope2)));
		assertThat(emptyGeoScope, equalTo(Optional.empty()));

	}
	
	@Test
	public void shouldReturnOptionalGeoScope_whenArgumentCapture() throws Exception {
		// Arrange
			when(geoScopeRepository.findAll(ArgumentMatchers.<Example<GeoScope>>any())).thenReturn(
						Arrays.asList(exampleGeoScope,exampleGeoScope2)
				);
			
		
		// Act
		List<GeoScope> retrievedGeoScopes = geoScopeService.findPreferredGeoScopes("DEDUS", "");
		
		//

		// Assert Captured input of geoScopeRepository 
		Mockito.verify(geoScopeRepository).findAll(geoScopeCaptor.capture());
		
		Example<GeoScope> capturedExample= geoScopeCaptor.getValue();
		assertThat(capturedExample.getProbe().getGeoScopeType(), equalTo("L"));
		assertThat(capturedExample.getMatcher().getIgnoredPaths(), hasItems("id", "countryCode", "locationCode"));

		// Assert returned result of geoScopeService 

		assertThat(retrievedGeoScopes, hasItems(exampleGeoScope));
		
	}
	

}
