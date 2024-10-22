package hello.login.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

// modelAttribute를 쓸경우 html input의 name과 필드명을 일치시켜줘야함.
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(String itemName, Integer itemPrice, Integer quantity) {
        this.itemName = itemName;
        this.price = itemPrice;
        this.quantity = quantity;
    }
}
