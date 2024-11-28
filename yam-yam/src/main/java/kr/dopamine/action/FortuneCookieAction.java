package kr.dopamine.action;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;

public class FortuneCookieAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//포춘쿠키
		List<String> fortuneMessage = Arrays.asList(
			"오늘은 당신의 날이 될 것입니다.",
			"행운은 준비된 사람에게 찾아옵니다.",
			"새로운 기회가 곧 다가옵니다.",
			"당신의 노력은 결실을 맺을 것입니다.",
			"네잎 클로버의 꽃말은 행운, 세잎 클로버의 꽃말은 행복",
			"중요한 결정을 내릴 순간이 다가오고 있습니다.",
			"오늘은 새로운 시작을 할 완벽한 날입니다.",
			"작은 변화가 큰 성과를 가져올 것입니다.",
			"긍정적인 생각이 당신의 미래를 밝힙니다.",
			"좋은 일은 항상 예기치 않게 찾아옵니다.",
			"용기를 내면 새로운 길이 열립니다.",
			"당신의 열정이 성공으로 이어질 것입니다.",
			"친절은 언제나 당신에게 돌아옵니다.",
			"세상이 당신의 노력을 알아보게 될 것입니다.",
			"나쁜 일이 지나고 좋은 일이 찾아올 것입니다.",
			"중요한 것은 행운을 위해 행복을 버리지 않는 것입니다.",
			"당신의 꿈은 이루어질 준비가 되어 있습니다.",
			"힘든 시간이 지나고 평화로운 시간이 올 것입니다.",
			"오늘 하루도 당신의 웃음으로 채워지길 바랍니다.",
			"작은 친절이 큰 기쁨을 가져올 것입니다."
		);
		String randomMessage = fortuneMessage.get(new Random().nextInt(fortuneMessage.size()));
		request.setAttribute("randomMessage", randomMessage);
		
		return "dopamine/fortuneCookie.jsp";
	}

}
