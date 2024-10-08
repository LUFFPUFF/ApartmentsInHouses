package apartment.in.houses.service.servlet;

import apartment.in.houses.service.exceprion.NotFoundException;
import apartment.in.houses.service.service.apartmentservice.ApartmentService;
import apartment.in.houses.data_access.apartmentsdatabase.entities.Apartment;
import apartment.in.houses.util.DI.context.ApplicationContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

@WebServlet(name = "apartment", value = "/apartment")
public class ApartmentServlet extends HttpServlet {

    private ApplicationContext context = new ApplicationContext("java");
    private ApartmentService service = context.getBean(ApartmentService.class);

    public ApartmentServlet() throws InvocationTargetException, InstantiationException, IllegalAccessException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Apartment apartment;
        try {
            apartment = service.getId(3);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        resp.setContentType("text/html");

        PrintWriter printWriter = resp.getWriter();

        printWriter.write("<html><body>");
        printWriter.write("<h1> Прайс на квартиру: </h1>");
        printWriter.write("<h2>" + apartment.getPrice() + "</h2>");
        printWriter.write("</body></html>");
        printWriter.close();
    }
}
