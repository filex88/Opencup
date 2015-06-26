package it.dipe.opencup.controllers;

import it.dipe.opencup.facade.ProgettoFacade;
import it.dipe.opencup.model.Contattaci;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

@Controller
@RequestMapping("VIEW")
public class ContattaciPortletController {

	@Autowired
	private ProgettoFacade progettoFacade;

	@RenderMapping
	public String renderRequest(RenderRequest renderRequest,
			RenderResponse renderResponse, Model model,
			@ModelAttribute("contattaci") Contattaci contattaci) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		model.addAttribute("jsFolder",themeDisplay.getPathThemeJavaScript());
		model.addAttribute("imgFolder",themeDisplay.getPathThemeImages());
		return handleRenderRequest(renderRequest, renderResponse, model,
				contattaci);
	}
	

	@RenderMapping(params = "render=action")
	public String handleRenderRequest(RenderRequest renderRequest,
			RenderResponse renderResponse, Model model,
			@ModelAttribute("contattaci") Contattaci contattaci) {

		return "contattaci-view";
	}

	private void checkCaptcha(PortletRequest request) throws Exception {
		String enteredCaptchaText = ParamUtil.getString(request, "captchaText");

		PortletSession session = request.getPortletSession();
		String captchaText = getCaptchaValueFromSession(session);
		if (Validator.isNull(captchaText)) {
			throw new Exception(
					"Internal Error! Captcha text not found in session");
		}
		if (!captchaText.equals(enteredCaptchaText)) {
			throw new Exception("Invalid captcha text. Please reenter.");
		}
	}

	private String getCaptchaValueFromSession(PortletSession session) {
		Enumeration<String> atNames = session.getAttributeNames();
		while (atNames.hasMoreElements()) {
			String name = atNames.nextElement();
			if (name.contains("CAPTCHA_TEXT")) {
				return (String) session.getAttribute(name);
			}
		}
		return null;
	}

	public void processAction(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {
		try {
			checkCaptcha(actionRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@ActionMapping(params = "action=inviaEmail")
	public void validateCaptcha(ActionRequest actionRequest,
			ActionResponse actionResponse, Model model,
			@ModelAttribute("contattaci") Contattaci contattaci)
			throws IOException, PortletException {

		model.addAttribute("contattaci", contattaci);
		try {
			String cup = ParamUtil.getString(actionRequest, "cup");
			String nome = ParamUtil.getString(actionRequest, "nome");
			String cognome = ParamUtil.getString(actionRequest, "cognome");
			String email = ParamUtil.getString(actionRequest, "email");
			String tipoMessaggio = ParamUtil.getString(actionRequest,
					"tipoMessaggio");
			String oggetto = ParamUtil.getString(actionRequest, "oggetto");
			String messaggio = ParamUtil.getString(actionRequest, "messaggio");

			System.out.println("CUP  :" + cup);
			System.out.println("Nome  :" + nome);

			CaptchaUtil.check(actionRequest);
			contattaci.setDataInserimento(new Date());
			progettoFacade.saveContattaci(contattaci);
			// System.out.println("CAPTCHA validated successfully");
			MailMessage mailMessage = new MailMessage();
			InternetAddress addressTo = new InternetAddress();
			InternetAddress addressFrom = new InternetAddress();
			addressTo.setAddress("assistenzacup@assistenzacup.com");
			addressFrom.setAddress(email);
			mailMessage.setFrom(addressFrom);
			mailMessage.setTo(addressTo);
			mailMessage.setSubject(contattaci.getOggetto());
			mailMessage.setBody(contattaci.getMessaggio());
			MailServiceUtil.sendEmail(mailMessage);

		} catch (CaptchaException e) {
			SessionErrors.add(actionRequest, "errorMessage");
		}

	}

	@ResourceMapping
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		try {
			com.liferay.portal.kernel.captcha.CaptchaUtil.serveImage(
					resourceRequest, resourceResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
