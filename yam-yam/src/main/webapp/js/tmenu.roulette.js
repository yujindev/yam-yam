$(function () {
    const canvas = document.getElementById("roulette_canvas");
    const ctx = canvas.getContext("2d");
    const spinButton = $("#spin_roulette");
    const refreshButton = $("#refresh_menu");
    const resultText = $("#result_text");

    let menuListsc = []; // 룰렛 섹션 (서버 데이터로 채워짐)

    // 룰렛 섹션 색상 (고정된 색상)
    const sections = [
        "#3CA3E3", "#FF6F0F", "#00A05A", "#38438F",
        "#EFB6C8", "#786953", "#FF7F3E", "#FDE7BB"
    ];

    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const radius = centerX - 10;
    let currentAngle = 0;

    // 룰렛 그리기
    function drawRoulette() {
        if (menuListsc.length === 0) {
            resultText.text("메뉴가 없습니다. 데이터를 확인하세요.");
            return;
        }

        const anglePerSection = (2 * Math.PI) / menuListsc.length;
        for (let i = 0; i < menuListsc.length; i++) {
            const startAngle = i * anglePerSection;
            const endAngle = startAngle + anglePerSection;

            ctx.beginPath();
            ctx.moveTo(centerX, centerY);
            ctx.arc(centerX, centerY, radius, startAngle, endAngle);
            ctx.closePath();
            ctx.fillStyle = sections[i % sections.length]; // 색상 순환
            ctx.fill();
            ctx.stroke();

            // 텍스트 추가
            ctx.save();
            ctx.translate(centerX, centerY);
            ctx.rotate(startAngle + anglePerSection / 2);
            ctx.textAlign = "right";
            ctx.fillStyle = "#fff";
            ctx.font = "16px Arial";
            ctx.fillText(menuListsc[i].label, radius - 20, 10);
            ctx.restore();
        }

    }

    // 룰렛 회전 애니메이션
    function spinRoulette(onSpinEnd) {
        const spinAngle = Math.random() * 360 + 720; // 최소 2바퀴 + 랜덤 각도
        const spinTime = 500; // 회전 시간
        const spinStart = Date.now();

        function animate() {
            const currentTime = Date.now();
            const elapsedTime = currentTime - spinStart;

            if (elapsedTime < spinTime) {
                const progress = elapsedTime / spinTime;
                currentAngle = progress * spinAngle;
                ctx.clearRect(0, 0, canvas.width, canvas.height);
                ctx.save();
                ctx.translate(centerX, centerY);
                ctx.rotate((currentAngle * Math.PI) / 180);
                ctx.translate(-centerX, -centerY);
                drawRoulette();
                ctx.restore();

                requestAnimationFrame(animate);
            } else {
                onSpinEnd(); // 회전 종료 후 콜백 실행
            }
        }
        animate();
    }

    // 초기 룰렛 데이터 로드 및 그리기
    function fetchRouletteData() {
        $.ajax({
            url: "roulette.do", // AJAX 요청 URL
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.result === "success") {
                    menuListsc = data.data.map((item) => ({
                        label: item.tm_menu // 서버에서 전달받은 메뉴 이름
                    }));
                    drawRoulette(); // 룰렛 그리기
                } else {
                    resultText.text("항목이 없습니다.");
                }
            },
            error: function () {
                resultText.text("데이터를 가져오는 데 실패했습니다.");
            }
        });
    }

    // 버튼 클릭 이벤트
    spinButton.click(function () {
        if (menuListsc.length === 0) {
            resultText.text("룰렛 데이터를 불러오지 못했습니다.");
            return;
        }

        resultText.text("룰렛이 돌아가는 중...");

        // 룰렛 회전 애니메이션
        spinRoulette(() => {
            const currentAngleNormalized = (360 - (currentAngle % 360)) % 360;
            const selectedIndex = Math.floor(currentAngleNormalized / (360 / menuListsc.length)) % menuListsc.length;

            const selectedSection = menuListsc[selectedIndex];
            resultText.text("선택된 메뉴 : " + selectedSection.label);
        });
    });

    // 새로고침 버튼 클릭 이벤트
    refreshButton.click(function () {
        menuListsc = [];
        resultText.text("메뉴를 새로고침 중입니다...");
        fetchRouletteData();
    });

    // 페이지 로드 시 룰렛 데이터 가져오기
    fetchRouletteData();
});
