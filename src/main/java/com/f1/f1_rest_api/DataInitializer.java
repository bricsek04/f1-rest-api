package com.f1.f1_rest_api;

import com.f1.f1_rest_api.entity.CircuitEntity;
import com.f1.f1_rest_api.entity.ConstructorEntity;
import com.f1.f1_rest_api.entity.DriverEntity;
import com.f1.f1_rest_api.repository.CircuitRepository;
import com.f1.f1_rest_api.repository.ConstructorRepository;
import com.f1.f1_rest_api.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ConstructorRepository constructorRepository;
    private final DriverRepository driverRepository;
    private final CircuitRepository circuitRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (constructorRepository.count() > 0) {
            return;
        }

        ConstructorEntity redBull = constructorRepository.save(ConstructorEntity.builder()
                .name("Oracle Red Bull Racing")
                .baseLocation("Milton Keynes, United Kingdom")
                .engineSupplier("Honda RBPT")
                .teamPrincipalUsername("christian_horner")
                .championshipPoints(589)
                .build());

        ConstructorEntity mclaren = constructorRepository.save(ConstructorEntity.builder()
                .name("McLaren Formula 1 Team")
                .baseLocation("Woking, United Kingdom")
                .engineSupplier("Mercedes")
                .teamPrincipalUsername("andrea_stella")
                .championshipPoints(666)
                .build());

        ConstructorEntity ferrari = constructorRepository.save(ConstructorEntity.builder()
                .name("Scuderia Ferrari")
                .baseLocation("Maranello, Italy")
                .engineSupplier("Ferrari")
                .teamPrincipalUsername("fred_vasseur")
                .championshipPoints(652)
                .build());

        DriverEntity verstappen = driverRepository.save(DriverEntity.builder()
                .fullName("Max Verstappen")
                .code("VER")
                .permanentNumber(1)
                .nationality("Dutch")
                .points(437)
                .superLicencePenaltyPoints(4)
                .constructor(redBull)
                .build());

        DriverEntity norris = driverRepository.save(DriverEntity.builder()
                .fullName("Lando Norris")
                .code("NOR")
                .permanentNumber(4)
                .nationality("British")
                .points(374)
                .superLicencePenaltyPoints(0)
                .constructor(mclaren)
                .build());

        DriverEntity piastri = driverRepository.save(DriverEntity.builder()
                .fullName("Oscar Piastri")
                .code("PIA")
                .permanentNumber(81)
                .nationality("Australian")
                .points(292)
                .superLicencePenaltyPoints(2)
                .constructor(mclaren)
                .build());

        DriverEntity leclerc = driverRepository.save(DriverEntity.builder()
                .fullName("Charles Leclerc")
                .code("LEC")
                .permanentNumber(16)
                .nationality("Monegasque")
                .points(356)
                .superLicencePenaltyPoints(1)
                .constructor(ferrari)
                .build());

        circuitRepository.save(CircuitEntity.builder()
                .name("Hungaroring")
                .country("Hungary")
                .lengthMeters(4381)
                .laps(70)
                .lapRecordTime("1:16.627")
                .lapRecordDriver(verstappen)
                .build());

        circuitRepository.save(CircuitEntity.builder()
                .name("Circuit de Spa-Francorchamps")
                .country("Belgium")
                .lengthMeters(7004)
                .laps(44)
                .lapRecordTime("1:44.701")
                .lapRecordDriver(verstappen)
                .build());

        circuitRepository.save(CircuitEntity.builder()
                .name("Autodromo Nazionale Monza")
                .country("Italy")
                .lengthMeters(5793)
                .laps(53)
                .lapRecordTime("1:21.046")
                .lapRecordDriver(norris)
                .build());
    }
}