package com.blibli.belajar.design.patterns.facade;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

public class FacadeApplication {

  public static interface AddressService {

    void save(String customerId, String address, String city);

    void remove(String customerId, String address, String city);

    void update(String customerId, String address, String city);
  }

  public static class AddressController {

    @Setter
    private AddressService addressService;

    public void saveAddress(String customerId, String address, String city) {
      System.out.println("Controller");
      addressService.save(customerId, address, city);
    }
  }

  public static class AddressServiceImplPostgre implements AddressService {

    @Override
    public void save(String customerId, String address, String city) {
      System.out.println("Complicated Implementation Postgre");
    }

    @Override
    public void remove(String customerId, String address, String city) {
      System.out.println("Complicated Implementation Postgre");
    }

    @Override
    public void update(String customerId, String address, String city) {
      System.out.println("Complicated Implementation Postgre");
    }
  }

  public static class AddressServiceImplMongo implements AddressService {

    @Override
    public void save(String customerId, String address, String city) {
      System.out.println("Complicated Implementation Mongo");
    }

    @Override
    public void remove(String customerId, String address, String city) {
      System.out.println("Complicated Implementation Mongo");
    }

    @Override
    public void update(String customerId, String address, String city) {
      System.out.println("Complicated Implementation Mongo");
    }
  }

  @SpringBootApplication
  public static class Application {

    // jika ada 2 Bean dengan tipe data atau nama yang sama
    // bisa di solve dengan menambahkan @Primary di bawah Bean
    // atau menghapus salah satu @Bean atau dengan menambahkan
    // @Qualifier di method yang autoriwed/perlu di inject sama
    // Bean (cek AddressController)
    @Bean
    public AddressService addressServicePostgre() {
      return new AddressServiceImplPostgre();
    }

    @Bean
    public AddressService addressServiceMongo() {
      return new AddressServiceImplMongo();
    }

    @Bean
    public AddressController addressController(
        @Qualifier("addressServiceMongo") AddressService addressService) {
      AddressController controller = new AddressController();
      controller.setAddressService(addressService);
      return controller;
    }
  }

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Application.class);
    AddressController controller = context.getBean(AddressController.class);

    controller.saveAddress("", "", "");
  }
}
