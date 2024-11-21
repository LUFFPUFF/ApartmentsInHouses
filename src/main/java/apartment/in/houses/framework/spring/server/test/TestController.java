package apartment.in.houses.framework.spring.server.test;

import apartment.in.houses.data_access.apartmentsdatabase.entitie.Apartment;
import apartment.in.houses.data_access.apartmentsdatabase.entitie.Permission;
import apartment.in.houses.data_access.apartmentsdatabase.repositories.ApartmentRepository;
import apartment.in.houses.framework.spring.DI.context.ApplicationContext;
import apartment.in.houses.framework.spring.annotation.*;
import apartment.in.houses.framework.spring.http.httprequest.HttpRequest;
import apartment.in.houses.framework.spring.http.httpresponse.HttpResponse;
import apartment.in.houses.service.exception.InvalidException;
import apartment.in.houses.service.exception.NotFoundException;
import apartment.in.houses.service.service.apartmentservice.ApartmentService;
import apartment.in.houses.service.service.apartmentservice.PermissionService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class TestController {
    private ApplicationContext context = new ApplicationContext("java");
    private ApartmentService service = context.getBean(ApartmentService.class);
    private PermissionService permissionService = context.getBean(PermissionService.class);

    public Apartment getApartment(int id) {
        try {
            return service.getId(id);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/welcome")
    public void welcome(HttpRequest request, HttpResponse response) {
        response.setHtmlBody("<h1>Это сервер Никиты!</h1>");
    }

    @GetMapping(value = "/user/{id}")
    public String getUser(@PathVariable(value = "id") int userId) {
        return "<h1>User id: " + userId + "</h1>";
    }

    @PostMapping("/users")
    public String createUser(@RequestBody User user) {
        System.out.println("User received: " + user.getName());
        return "<h1>User Created: " + user.getName() + "</h1>";
    }

    @GetMapping(value = "/apartment/{id}")
    public String apartment(@PathVariable("id") int id) {
        Apartment apartment = getApartment(id);
        return "<h1>" + apartment + "</h1>";
    }

    @PostMapping(value = "/create")
    public void createPermission(@RequestBody Permission permission) {
        System.out.println("Received permission: " + permission);
        try {
            permissionService.add(permission);
        } catch (InvalidException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/sub")
    public String submit(HttpRequest request) {
        return "<h1>Data submitted:</h1><pre>" + request.getBody().toString() + "</pre>";
    }

    @OptionsMapping(value = "/opt")
    public String options(HttpRequest request, HttpResponse response) {
        response.addHeader("Allow", "GET, POST");
        return "<h1>options:</h1><pre>" + request.getHeader("Allow") + "</pre>";
    }
}
