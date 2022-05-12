package csci5308.fall21.appHub.controller.payment;

import java.util.Iterator;
import java.util.List;
import com.paypal.api.payments.Links;
import static csci5308.fall21.appHub.util.PaymentConstants.*;

public class Paypal {

    /**
     *
     * @author Pallavi Cherukupalli
     */
    public String routeToPaypal(List<Links> paymentLinks) {
        Iterator<Links> iterator = paymentLinks.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getRel().equals(approvalUrl)) {
                return iterator.next().getHref();
            }
        }
        return null;
    }
}
