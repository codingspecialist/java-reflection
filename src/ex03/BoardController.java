package ex03;

@Controller
public class BoardController {

    @RequestMapping(uri = "/save")
    public void save(){
        System.out.println("save 호출됨");
    }
}
