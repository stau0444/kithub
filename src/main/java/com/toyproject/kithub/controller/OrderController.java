package com.toyproject.kithub.controller;

import com.toyproject.kithub.domain.Member;
import com.toyproject.kithub.domain.Order;
import com.toyproject.kithub.domain.item.Item;
import com.toyproject.kithub.repository.MemberRepository;
import com.toyproject.kithub.repository.OrderRepository;
import com.toyproject.kithub.repository.OrderSearch;
import com.toyproject.kithub.service.ItemService;
import com.toyproject.kithub.service.MemberService;
import com.toyproject.kithub.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String orderForm(Model model){

        List<Item> item = itemService.findItem();
        List<Member> members = memberService.findMembers();
        model.addAttribute("items" , item);
        model.addAttribute("members",members);
        model.addAttribute("order");
        return "order/orderForm";
    }
    @PostMapping("/order")
    public RedirectView order(Long memberId , Long itemId , int count,RedirectAttributes attributes,
    @ModelAttribute String message){
        Long order = orderService.order(memberId, itemId, count);
        if(order != null){
            attributes.addFlashAttribute("message","주문이 성공했습니다.");
        }
        return new RedirectView("/",true);
    }

    @GetMapping("/orders")
    public String getOrderList(Model model){
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orderSearch",new OrderSearch());
        model.addAttribute("orders",orders);
        return "order/orders";
    }



}
