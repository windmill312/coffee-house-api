package com.sychev.coffeehouse.grpc.service.v1.impl;

import com.sychev.coffeehouse.converter.ModelConverter;
import com.sychev.coffeehouse.grpc.model.v1.*;
import com.sychev.coffeehouse.grpc.service.v1.CoffeeHouseServiceV1Grpc;
import com.sychev.coffeehouse.model.entity.CafeEntity;
import com.sychev.coffeehouse.service.CoffeeHouseService;
import com.sychev.common.grpc.model.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.UUID;
import java.util.stream.Collectors;

@GRpcService
public class CoffeeHouseServiceV1GrpcImpl extends CoffeeHouseServiceV1Grpc.CoffeeHouseServiceV1ImplBase {

    private final CoffeeHouseService coffeeHouseService;

    @Autowired
    public CoffeeHouseServiceV1GrpcImpl(CoffeeHouseService coffeeHouseService) {
        this.coffeeHouseService = coffeeHouseService;
    }

    @Override
    public void getAllCafes(
            GGetAllCafesRequest request,
            StreamObserver<GGetAllCafesResponse> responseObserver) {

        Page<CafeEntity> cafes = coffeeHouseService.getAllCafes(ModelConverter.convert(request.getPageable()));

        responseObserver.onNext(GGetAllCafesResponse.newBuilder()
                .setPage(ModelConverter.convert(cafes))
                .addAllCafes(cafes.getContent()
                        .stream()
                        .map(ModelConverter::convert)
                        .collect(Collectors.toList()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllCafesAroundClient(
            GGetAllCafesAroundClientRequest request,
            StreamObserver<GGetAllCafesAroundClientResponse> responseObserver) {

        Page<CafeEntity> cafes = coffeeHouseService.getAllCafesAroundClient(ModelConverter.convert(request.getPageable()),
                request.getLocation().getLatitude(),
                request.getLocation().getLongitude());

        responseObserver.onNext(GGetAllCafesAroundClientResponse.newBuilder()
                .setPage(ModelConverter.convert(cafes))
                .addAllCafes(cafes.getContent()
                        .stream()
                        .map(ModelConverter::convert)
                        .collect(Collectors.toList()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getCafe(
            GGetCafeRequest request,
            StreamObserver<GGetCafeResponse> responseObserver) {

        CafeEntity cafe = coffeeHouseService.getCafeByUid(ModelConverter.convert(request.getCafeUid()));

        responseObserver.onNext(GGetCafeResponse.newBuilder()
                .setCafe(ModelConverter.convert(cafe))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void addCafe(
            GAddCafeRequest request,
            StreamObserver<GAddCafeResponse> responseObserver) {

        UUID cafeUid = coffeeHouseService.addCafe(ModelConverter.convert(request.getCafe()));

        responseObserver.onNext(GAddCafeResponse.newBuilder()
                .setCafeUid(ModelConverter.convert(cafeUid))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateCafe(
            GUpdateCafeRequest request,
            StreamObserver<Empty> responseObserver) {

        coffeeHouseService.updateCafe(
                ModelConverter.convert(request.getCafe())
                        .setCafeUid(ModelConverter.convert(request.getCafe().getCafeUid())));

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeCafe(
            GRemoveCafeRequest request,
            StreamObserver<Empty> responseObserver) {

        coffeeHouseService.removeCafe(ModelConverter.convert(request.getCafeUid()));

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

}
