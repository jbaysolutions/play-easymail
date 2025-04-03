package controllers;

import com.google.inject.Inject;
import com.jbaysolutions.play.module.mail.DefaultMailer;
import com.jbaysolutions.play.module.mail.Mailer.Mail;
import com.jbaysolutions.play.module.mail.Mailer.Mail.Body;
import com.jbaysolutions.play.module.mail.Mailer.MailerFactory;
import org.apache.commons.mail.EmailAttachment;
import play.Environment;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.filters.csrf.CSRF;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;

public class HomeController extends Controller {
	private final Environment env;

	private final DefaultMailer defaultMailer;

	private final FormFactory formFactory;

	private final MailerFactory customMailer;

	private final MessagesApi messagesApi;

	private final Form<MailMe> FORM;

	public static class MailMe {
		@Email
		@Required
		private String email;

		public String getEmail() {
			return this.email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}

	@Inject
	public HomeController(final Environment env, final DefaultMailer defaultMailer, final FormFactory formFactory,
			final MessagesApi messagesApi,
			final MailerFactory mailerFactory) {
		this.env = env;
		this.defaultMailer = defaultMailer;
		this.formFactory = formFactory;
		this.customMailer = mailerFactory;
		this.messagesApi = messagesApi;
		FORM = formFactory.form(MailMe.class);
	}

	public Result index(final Http.Request request) {
		CSRF.Token csrfToken = CSRF.getToken(request).orElseThrow(() -> new RuntimeException("CSRF token not found"));
		return ok(index.render(FORM, csrfToken, request, messagesApi.preferred(request)));
	}

	public Result sendMail(final Http.Request request) {
		final Form<MailMe> filledForm = FORM.bindFromRequest(request);
		if (filledForm.hasErrors()) {
			CSRF.Token csrfToken = CSRF.getToken(request).orElseThrow(() -> new RuntimeException("CSRF token not found"));
			return badRequest(index.render(filledForm, csrfToken, request, messagesApi.preferred(request)));
		} else {
			final String email = filledForm.get().email;
			final Body body = new Body(views.txt.email.body.render().toString(),
					views.html.email.body.render().toString());

			{
				// simple usage
				defaultMailer.sendMail("play-easymail | it works!", body, email);
			}

			{
				// advanced usage
				final Mail customMail = new Mail("play-easymail | advanced", body, new String[] { email });
				customMail.addHeader("Reply-To", email);
				customMail.addAttachment("attachment.pdf", env.getFile("conf/sample.pdf"));
				byte[] data = "data".getBytes();
				customMail.addAttachment("data.txt", data, "text/plain", "A simple file", EmailAttachment.INLINE);
				defaultMailer.sendMail(customMail);
			}

			return redirect(routes.HomeController.index()).flashing("message", "2 mails to '" + email + "' have been sent successfully!");
		}
	}

}
