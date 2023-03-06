// tag::head[]
package tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;

@Slf4j
@Controller
@RequestMapping("/design")
/**
 * Spring MVC 的 @RequestMapping 注解能够处理 HTTP 请求的方法, 比如 GET, PUT, POST, DELETE 以及 PATCH。 *
 * 所有的请求默认都会是 HTTP GET 类型的。
 */
public class DesignTacoController {

//end::head[]

@ModelAttribute
/**
 * @ModelAttribute标注可被应用在方法或方法参数上。 *
 * 标注在方法上的@ModelAttribute说明方法是用于添加一个或多个属性到model上。这样的方法能接受与@RequestMapping标注相同的参数类型，只不过不能直接被映射到具体的请求上。 *
 * 在同一个控制器中，标注了@ModelAttribute的方法实际上会在@RequestMapping方法之前被调用。
 *
 * 一个控制器可以拥有多个@ModelAttribute方法。同个控制器内的所有这些方法，都会在@RequestMapping方法之前被调用。 *
 * @ModelAttribute方法也可以定义在@ControllerAdvice标注的类中，并且这些@ModelAttribute可以同时对许多控制器生效。
 *
 *
 */
public void addIngredientsToModel(Model model) {
	List<Ingredient> ingredients = Arrays.asList(
	  new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
	  new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
	  new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
	  new Ingredient("CARN", "Carnitas", Type.PROTEIN),
	  new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
	  new Ingredient("LETC", "Lettuce", Type.VEGGIES),
	  new Ingredient("CHED", "Cheddar", Type.CHEESE),
	  new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
	  new Ingredient("SLSA", "Salsa", Type.SAUCE),
	  new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
	);
	
	Type[] types = Type.values();
	for (Type type : types) {
	  model.addAttribute(type.toString().toLowerCase(),
	      filterByType(ingredients, type));
	}
}
	
//tag::showDesignForm[]
  @GetMapping
  public String showDesignForm(Model model) {
    model.addAttribute("design", new Taco());
    return "design";
  }

//end::showDesignForm[]

/*
//tag::processDesign[]
  @PostMapping
  public String processDesign(Design design) {
    // Save the taco design...
    // We'll do this in chapter 3
    log.info("Processing design: " + design);

    return "redirect:/orders/current";
  }

//end::processDesign[]
 */

//tag::processDesignValidated[]
  @PostMapping
  /**
   * 进行了数据绑定后，则可能会出现一些错误，比如没有提供必须的字段、类型转换过程的错误等。若想检查这些错误，可以在标注了@ModelAttribute的参数紧跟着声明一个BindingResult参数：
   * 使用@ModelAttribute从URL中获取design参数：@RequestMapping(path = "/accounts/{designe}", method = RequestMethod.POST)
   * 使用@Valid,则验证器会被自动调用。
   */
  public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors, Model model) {
    if (errors.hasErrors()) {
      return "design";
    }

    // Save the taco design...
    // We'll do this in chapter 3
    log.info("Processing design: " + design);

    return "redirect:/orders/current";
  }

//end::processDesignValidated[]

//tag::filterByType[]
  private List<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }

//end::filterByType[]
// tag::foot[]
}
// end::foot[]
