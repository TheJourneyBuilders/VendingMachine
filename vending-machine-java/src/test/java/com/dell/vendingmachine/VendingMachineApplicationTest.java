package com.dell.vendingmachine;

import com.dell.vendingmachine.model.Product;
import com.dell.vendingmachine.model.VendingMachine;
import com.dell.vendingmachine.repository.VendingMachineRepository;
import com.dell.vendingmachine.service.VendingMachineService;
import com.dell.vendingmachine.service.impl.VendingMachineImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class VendingMachineApplicationTest {

	@Test
	void contextLoads() {
	}

	@Test
	public void shouldStoreMoney_whenAddingMoney() {
		VendingMachine vm = VendingMachine.builder()
				.id(1L)
				.address("Test Machine")
				.credit(0)
				.products(new ArrayList<>())
				.build();

		VendingMachineRepository repositoryMock = Mockito.mock(VendingMachineRepository.class);
		Mockito.when(repositoryMock.getById(1L)).thenReturn(vm);
		Mockito.when(repositoryMock.save(vm)).thenReturn(vm);

		VendingMachineService service = new VendingMachineImpl(repositoryMock);

		service.AddCredit(1L, 0.5D);

		assertEquals(0.5D, vm.getCredit());
	}

	@Test
	public void shouldNotAllowBuy_whenMoneyIsNotEnough() {
		Product p1 = Product.builder()
				.productId(1L)
				.name("Guarana")
				.quantity(5)
				.value(2)
				.build();

		Product p2 = Product.builder()
				.productId(2L)
				.name("Mandarin")
				.quantity(5)
				.value(1)
				.build();

		VendingMachine vm = VendingMachine.builder()
				.id(1L)
				.address("Test Machine")
				.credit(0.5)
				.products(Arrays.asList(p1, p2))
				.build();

		VendingMachineRepository repositoryMock = Mockito.mock(VendingMachineRepository.class);
		Mockito.when(repositoryMock.getById(1L)).thenReturn(vm);
		Mockito.when(repositoryMock.save(vm)).thenReturn(vm);

		VendingMachineService service = new VendingMachineImpl(repositoryMock);

		Product product = service.BuyProduct(1L, 1);
		assertNull(product);
		assertEquals(0.5, vm.getCredit());
	}

	@Test
	public void shouldReturnProductAndDecreaseCredit_whenMoneyIsEnough() {
		Product p1 = Product.builder()
				.productId(1L)
				.name("Guarana")
				.quantity(5)
				.value(2)
				.build();

		Product p2 = Product.builder()
				.productId(2L)
				.name("Mandarin")
				.quantity(5)
				.value(1)
				.build();

		VendingMachine vm = VendingMachine.builder()
				.id(1L)
				.address("Test Machine")
				.credit(5)
				.products(Arrays.asList(p1, p2))
				.build();

		VendingMachineRepository repositoryMock = Mockito.mock(VendingMachineRepository.class);
		Mockito.when(repositoryMock.getById(1L)).thenReturn(vm);
		Mockito.when(repositoryMock.save(vm)).thenReturn(vm);

		VendingMachineService service = new VendingMachineImpl(repositoryMock);

		Product product = service.BuyProduct(1L, 1);
		assertNotNull(product);
		assertEquals(1L, product.getProductId());
		assertEquals(3, vm.getCredit());
	}
}
