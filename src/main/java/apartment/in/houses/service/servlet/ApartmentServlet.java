package apartment.in.houses.service.servlet;

import apartment.in.houses.data_access.apartmentsdatabase.entities.Apartment;
import apartment.in.houses.service.service.apartmentservice.ApartmentService;
import apartment.in.houses.util.DI.context.ApplicationContext;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

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
        this.apartmentService = context.getBean(ApartmentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        List<Apartment> apartments = apartmentService.getAll();
        StringBuilder htmlResponse = new StringBuilder();

        htmlResponse.append("<!DOCTYPE html>")
                .append("<html lang='en'>")
                .append("<head>")
                .append("<meta charset='UTF-8'>")
                .append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>")
                .append("<title>Available Apartments</title>")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; background-color: #f5f5f5; }")
                .append(".container { display: flex; flex-wrap: wrap; gap: 20px; justify-content: center; padding: 20px; }")
                .append(".apartment-card { background-color: #fff; border: 1px solid #ddd; border-radius: 8px; width: 300px; padding: 15px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }")
                .append(".apartment-title { font-size: 18px; font-weight: bold; margin-bottom: 10px; }")
                .append(".apartment-detail { margin-bottom: 8px; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<h1 style='text-align: center;'>Available Apartments</h1>")
                .append("<div class='container'>");

        for (Apartment apartment : apartments) {
            htmlResponse.append("<div class='apartment-card'>")
                    .append("<div class='apartment-title'>Apartment ID: ").append(apartment.getId()).append("</div>")
                    .append("<div class='apartment-detail'>Address: ").append(apartment.getHouseId().getAddress()).append("</div>")
                    .append("<div class='apartment-detail'>Total Area: ").append(apartment.getTotalArea()).append(" sq.m</div>")
                    .append("<div class='apartment-detail'>Living Area: ").append(apartment.getLivingArea()).append(" sq.m</div>")
                    .append("<div class='apartment-detail'>Rooms: ").append(apartment.getRooms()).append("</div>")
                    .append("<div class='apartment-detail'>Floor: ").append(apartment.getFloor()).append("</div>")
                    .append("<div class='apartment-detail'>Entrance: ").append(apartment.getEntrance()).append("</div>")
                    .append("<div class='apartment-detail'>Price: $").append(apartment.getPrice()).append("</div>")
                    .append("<div class='apartment-detail'>Status: ").append(apartment.getSaleCondition()).append("</div>")
                    .append("</div>");
        }

        htmlResponse.append("</div>")
                .append("</body>")
                .append("</html>");

        response.getWriter().write(htmlResponse.toString());
    }
}
