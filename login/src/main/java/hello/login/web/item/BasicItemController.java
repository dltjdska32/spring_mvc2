package hello.login.web.item;


import hello.login.domain.item.Item;
import hello.login.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "item/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable(name = "itemId") long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "item/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "item/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(@RequestParam(name = "itemName") String itemName, @RequestParam(name = "price") Integer price, @RequestParam(name = "quantity") Integer quantity, Model model) {
//
//        Item item = new Item(itemName, price, quantity);
//        itemRepository.save(item);
//        model.addAttribute("item", item);
//
//        return "redirect:/basic/items";
//    }

/*    @PostMapping("/add")
    public String addItemV2(@ModelAttribute(name = "item") Item item) {

        itemRepository.save(item);
//        model.addAttribute("item", item);

        return "redirect:/basic/items";
    }*/

   /* @PostMapping("/add")
    public String addItemV3(Item item) {

        itemRepository.save(item);
//        model.addAttribute("item", item);

        return "redirect:/basic/items";
    }*/

/*    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item) {

        itemRepository.save(item);
//        model.addAttribute("item", item);

        return "redirect:/basic/items";
    }*/

  /*  @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }*/


    @PostMapping("/add")
    public String addItemV6(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {

        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/items/{itemId}";


    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable(name = "itemId") long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "item/editForm";
    }


    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable(name = "itemId") Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/items/{itemId}"; //pathvariable itemid를 쓸수잇음
    }


    /**
     *  테스트용 데이터 추가
     * */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 100000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
