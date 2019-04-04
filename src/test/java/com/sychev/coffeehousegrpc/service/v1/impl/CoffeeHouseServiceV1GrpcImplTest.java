package com.sychev.coffeehousegrpc.service.v1.impl;

import com.sychev.coffeehouse.converter.ModelConverter;
import com.sychev.coffeehouse.grpc.model.v1.*;
import com.sychev.coffeehouse.grpc.service.v1.impl.CoffeeHouseServiceV1GrpcImpl;
import com.sychev.coffeehouse.model.entity.CafeEntity;
import com.sychev.coffeehouse.service.CoffeeHouseService;
import com.sychev.common.grpc.model.GPageable;
import com.sychev.common.grpc.model.GUuid;
import io.grpc.stub.StreamObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeHouseServiceV1GrpcImplTest {

    private static final UUID CAFE_UID = UUID.fromString("517df602-4ffb-4e08-9626-2a0cf2db4849");
    private static final Double LATITUDE = 123.0;
    private static final Double LONGITUDE = 456.0;
    private static final String NAME = "У Зины";
    private static final String DESCRIPTION = "У нас лучший кофе!";
    private static final UUID OWNER_UID = UUID.fromString("123df602-4ffb-4e08-9626-2a0cf2db4849");

    @Mock
    private CoffeeHouseService coffeeHouseService;

    @InjectMocks
    private CoffeeHouseServiceV1GrpcImpl coffeeHouseServiceV1Grpc;

    @Test
    public void getAllCafes() {

        Page<CafeEntity> serviceResponse = new PageImpl<>(Collections.singletonList(getMockObjСafeEntity()));

        when(coffeeHouseService.getAllCafes(any(Pageable.class))).thenReturn(serviceResponse);

        GGetAllCafesRequest request = GGetAllCafesRequest.newBuilder()
                .setPageable(GPageable.newBuilder().setPage(0).setSize(20).build())
                .build();

        StreamObserver<GGetAllCafesResponse> observer = mock(StreamObserver.class);

        coffeeHouseServiceV1Grpc.getAllCafes(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GGetAllCafesResponse> captor = ArgumentCaptor.forClass(GGetAllCafesResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GGetAllCafesResponse response = captor.getValue();
        assertEquals(response.getCafesList().size(), serviceResponse.getTotalElements());
        assertEquals(ModelConverter.convert(response.getCafesList().get(0).getOwnerUid()), serviceResponse.getContent().get(0).getOwnerUid());
        assertEquals(ModelConverter.convert(response.getCafesList().get(0).getCafeUid()), serviceResponse.getContent().get(0).getCafeUid());
        assertEquals(response.getCafesList().get(0).getDescription(), serviceResponse.getContent().get(0).getDescription());
        assertEquals(response.getCafesList().get(0).getCafeName(), serviceResponse.getContent().get(0).getName());
        assertEquals((Double)response.getCafesList().get(0).getLocation().getLatitude(), serviceResponse.getContent().get(0).getLatitude());
        assertEquals((Double)response.getCafesList().get(0).getLocation().getLongitude(), serviceResponse.getContent().get(0).getLongitude());
    }

    @Test
    public void getCafesAroundLocationAndLongitude() {

        Page<CafeEntity> serviceResponse = new PageImpl<>(Collections.singletonList(getMockObjСafeEntity()));

        when(coffeeHouseService.getAllCafesAroundClient(any(Pageable.class), eq(LATITUDE), eq(LONGITUDE))).thenReturn(serviceResponse);

        GGetAllCafesAroundClientRequest request = GGetAllCafesAroundClientRequest.newBuilder()
                .setPageable(GPageable.newBuilder().setPage(0).setSize(20).build())
                .setLocation(GLocation.newBuilder().setLongitude(LONGITUDE).setLatitude(LATITUDE).build())
                .build();

        StreamObserver<GGetAllCafesAroundClientResponse> observer = mock(StreamObserver.class);

        coffeeHouseServiceV1Grpc.getAllCafesAroundClient(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GGetAllCafesAroundClientResponse> captor = ArgumentCaptor.forClass(GGetAllCafesAroundClientResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GGetAllCafesAroundClientResponse response = captor.getValue();
        assertEquals(response.getCafesList().size(), serviceResponse.getTotalElements());
        assertEquals(ModelConverter.convert(response.getCafesList().get(0).getOwnerUid()), serviceResponse.getContent().get(0).getOwnerUid());
        assertEquals(ModelConverter.convert(response.getCafesList().get(0).getCafeUid()), serviceResponse.getContent().get(0).getCafeUid());
        assertEquals(response.getCafesList().get(0).getDescription(), serviceResponse.getContent().get(0).getDescription());
        assertEquals(response.getCafesList().get(0).getCafeName(), serviceResponse.getContent().get(0).getName());
        assertEquals((Double)response.getCafesList().get(0).getLocation().getLatitude(), serviceResponse.getContent().get(0).getLatitude());
        assertEquals((Double)response.getCafesList().get(0).getLocation().getLongitude(), serviceResponse.getContent().get(0).getLongitude());

    }

    @Test
    public void getCafe() {

        CafeEntity serviceResponse = getMockObjСafeEntity();

        when(coffeeHouseService.getCafeByUid(any(UUID.class))).thenReturn(serviceResponse);

        GGetCafeRequest request = GGetCafeRequest.newBuilder()
                .setCafeUid(GUuid.newBuilder().setUuid(CAFE_UID.toString()).build())
                .build();

        StreamObserver<GGetCafeResponse> observer = mock(StreamObserver.class);

        coffeeHouseServiceV1Grpc.getCafe(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GGetCafeResponse> captor = ArgumentCaptor.forClass(GGetCafeResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GGetCafeResponse response = captor.getValue();
        assertEquals(ModelConverter.convert(response.getCafe().getOwnerUid()), serviceResponse.getOwnerUid());
        assertEquals(ModelConverter.convert(response.getCafe().getCafeUid()), serviceResponse.getCafeUid());
        assertEquals(response.getCafe().getDescription(), serviceResponse.getDescription());
        assertEquals(response.getCafe().getCafeName(), serviceResponse.getName());
        assertEquals((Double)response.getCafe().getLocation().getLatitude(), serviceResponse.getLatitude());
        assertEquals((Double)response.getCafe().getLocation().getLongitude(), serviceResponse.getLongitude());

    }

    @Test
    public void addCafe() {

        when(coffeeHouseService.addCafe(any(CafeEntity.class))).thenReturn(CAFE_UID);

        GAddCafeRequest request = GAddCafeRequest.newBuilder()
                .setCafe(GCafeInfo.newBuilder()
                        .setCafeName(NAME)
                        .setCafeUid(ModelConverter.convert(CAFE_UID))
                        .setDescription(DESCRIPTION)
                        .setLocation(ModelConverter.convert(LATITUDE, LONGITUDE))
                        .setOwnerUid(ModelConverter.convert(OWNER_UID)))
                .build();

        StreamObserver<GAddCafeResponse> observer = mock(StreamObserver.class);

        coffeeHouseServiceV1Grpc.addCafe(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GAddCafeResponse> captor = ArgumentCaptor.forClass(GAddCafeResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GAddCafeResponse response = captor.getValue();
        assertEquals(response.getCafeUid().getUuid(), CAFE_UID.toString());

    }

    private CafeEntity getMockObjСafeEntity() {
        return new CafeEntity()
                .setLongitude(LONGITUDE)
                .setLatitude(LATITUDE)
                .setCafeUid(CAFE_UID)
                .setName(NAME)
                .setDescription(DESCRIPTION)
                .setOwnerUid(OWNER_UID);
    }

}
