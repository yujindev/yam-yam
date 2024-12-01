$(function () {
    const canvas = document.getElementById("name-canvas");
    const ctx = canvas.getContext("2d");
    const pickButton = $("#pick-draw");
    const refreshButton = $("#refresh-menu");
    const resultText = $("#result-text");

    // 이미지 로드
    const menuImage = new Image(); // 1번 이미지
    const resultImage = new Image(); // 3번 이미지
    menuImage.src = "../images/tmenudraw1.png"; // 1번 이미지 경로
    resultImage.src = "../images/tmenudraw3.png"; // 3번 이미지 경로

    const shuffleGIF = $("#shuffle-gif"); // 섞는 중 GIF 이미지
    let menuListsc = []; // 서버에서 가져온 메뉴 데이터

    // 서버에서 데이터 가져오기
    function fetchMenuData() {
        $.ajax({
            url: "draw.do",
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.result === "success") {
                    menuListsc = data.data.map((item) => ({
                        label: item.tm_menu,
                    }));
                    drawMenuList(); // 1번 이미지와 메뉴 리스트 표시
                } else {
                    resultText.text("항목이 없습니다.");
                }
            },
            error: function () {
                alert("서버와 통신하는 중 오류가 발생했습니다.");
                resultText.text("데이터를 가져오는 데 실패했습니다.");
            },
        });
    }

    // 캔버스에 1번 이미지와 메뉴 이름 표시
    function drawMenuList() {
        shuffleGIF.hide(); // 섞는 중 GIF 숨김
        canvas.style.display = "block"; // 캔버스 표시

        if (menuListsc.length === 0) {
            resultText.text("메뉴 데이터를 불러오지 못했습니다.");
            return;
        }

        ctx.clearRect(0, 0, canvas.width, canvas.height); // 캔버스 초기화
        ctx.drawImage(menuImage, 0, 0, canvas.width, canvas.height); // 1번 이미지 표시

        ctx.font = "20px Arial";
        ctx.textAlign = "center";
        ctx.fillStyle = "black";

        const startY = 50; // 첫 번째 텍스트 Y 좌표
        const lineHeight = 40; // 텍스트 간 간격

        menuListsc.forEach((menu, index) => {
            ctx.fillText(menu.label, canvas.width / 2, startY + index * lineHeight);
        });
    }

    // 섞는 중 GIF 표시
    function showShuffleGIF() {
        canvas.style.display = "none"; // 캔버스 숨김
        shuffleGIF.show(); // 섞는 중 GIF 표시
    }

    // 캔버스에 3번 이미지와 결과 표시
    function drawResult(selectedMenu) {
        shuffleGIF.hide(); // 섞는 중 GIF 숨김
        canvas.style.display = "block"; // 캔버스 표시

        ctx.clearRect(0, 0, canvas.width, canvas.height); // 캔버스 초기화
        ctx.drawImage(resultImage, 0, 0, canvas.width, canvas.height); // 3번 이미지 표시

        ctx.font = "30px Arial";
        ctx.fillStyle = "black"; // 결과 텍스트를 검정색으로 설정
        ctx.textAlign = "center";
        ctx.fillText("결과: " + selectedMenu.label, canvas.width / 2, canvas.height / 2);
    }

    // 제비뽑기 버튼 클릭 이벤트
    pickButton.click(function () {
        if (menuListsc.length === 0) {
            resultText.text("메뉴 데이터를 불러오지 못했습니다.");
            return;
        }

        showShuffleGIF(); // 섞는 중 GIF 표시

        setTimeout(() => {
            // 랜덤으로 메뉴 선택
            const randomIndex = Math.floor(Math.random() * menuListsc.length);
            const selectedMenu = menuListsc[randomIndex];
            drawResult(selectedMenu); // 결과 이미지와 선택된 메뉴 표시
        }, 2000); // 섞는 애니메이션 지속 시간 (2초)
    });

    // 새로고침 버튼 클릭 이벤트
    refreshButton.click(function () {
        location.reload(); // 페이지 새로고침
    });

    // 초기 데이터 가져오기
    fetchMenuData();
});
