package toy.project.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import toy.project.constant.ItemSellStatus;
import toy.project.entity.Item;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @SpringBootTest 통합 테스트를 위해 스프링 부트에서 제공하는 어노테이션으로 모든 Bean을 IoC 컨테이너에 등록함
 * @TestPropertySource 테스트 코드 실행 시 application.properties에 설정해 둔 값보다 application-test.properties에 같은 설정이 있다면 더 높은 순위를 부여함.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {


    /**
     * ItemRepository를 사용하기 위해서 @Autowired를 통해 의존성 주입 ( Bean )
     */
    @Autowired
    ItemRepository itemRepository;


    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(100000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println("savedItem.toString() = " + savedItem.toString());
    }


    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(100000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }


    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNameTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemName("테스트 상품 1");

        for (Item item : itemList) {
            System.out.println("item.toString() = " + item.toString());

        }
    }

    @Test
    @DisplayName("상품명, 상품 상세 설명 or 테스트")
    public void findByItemNameOrItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNameOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());

        }
    }


    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(100005);
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());

        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(100005);
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());

        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());

        }
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNative() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());

        }
    }
}