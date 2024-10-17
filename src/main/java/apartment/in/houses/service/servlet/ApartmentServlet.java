package apartment.in.houses.service.servlet;

import apartment.in.houses.data_access.apartmentsdatabase.entities.Apartment;
import apartment.in.houses.data_access.apartmentsdatabase.repositories.ApartmentRepository;
import apartment.in.houses.service.service.apartmentservice.ApartmentService;
import apartment.in.houses.util.DI.context.ApplicationContext;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/apartment")
public class ApartmentServlet extends HttpServlet {

    private ApartmentService apartmentService;

    public void init(ServletConfig servletConfig) {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ApplicationContext("java");
        this.apartmentService = new ApartmentService(new ApartmentRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String resourcePath = "/pages/index.html";
        InputStream inputStream = getServletContext().getResourceAsStream(resourcePath);

        String htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        List<Apartment> apartments = apartmentService.getAll().stream()
                .limit(5)
                .collect(Collectors.toList());

        StringBuilder apartmentsHtml = new StringBuilder();
        for (Apartment apartment : apartments) {
            apartmentsHtml.append("<div class=\"apartment\">")
                    .append("<p>Адрес: ").append(apartment.getHouseId().getAddress()).append("</p>")
                    .append("<p>Комнат: ").append(apartment.getRooms()).append("</p>")
                    .append("<p>Площадь: ").append(apartment.getTotalArea()).append(" м²</p>")
                    .append("</div>");
        }

        String modifiedHtml = htmlContent.replace("<!-- Динамическое содержимое о квартирах -->", apartmentsHtml.toString());

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(modifiedHtml);
    }

}
