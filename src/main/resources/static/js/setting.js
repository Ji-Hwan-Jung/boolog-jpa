function updateName() {

    const updateNameBtn = document.querySelector('#updateName');
    const updateNameToast = document.querySelector('#nameToast');

    const toast = new bootstrap.Toast(updateNameToast);

    const username = document.querySelector('#username');

    if (username.value.length < 1) {
        alert('이름을 입력해주세요');
        return;
    }

    const data = {
        name: this.username.value
    }

    fetch("/setting/update", {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    })
    .then((response) => response.text())
    .then((data) => {
        document.querySelector('#profile').setAttribute('href', '/@' + this.username.value);
        toast.show();
    })
    .catch((error) => {
        alert('오류 발생');
        window.location.href = '/setting';
    });
}

function updateIntro() {

    const updateIntroBtn = document.querySelector('#updateIntro');
    const updateIntroToast = document.querySelector('#introToast');

    const toast = new bootstrap.Toast(updateIntroToast);

    let introduction = document.querySelector('#introduction');

    const data = {
        introduction: introduction.value
    }

    fetch("/setting/update", {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    })
    .then((response) => response.text())
    .then((data) => {
        toast.show();
    })
    .catch((error) => {
        alert('오류 발생');
        window.location.href = '/setting';
    });
}

function withdrawal() {

    let result = confirm('회원탈퇴를 진행하시겠습니까?');

    if (result == true) {
        fetch("/setting/withdrawal", {
                method: 'DELETE',
            })
            .then((response) => response.text())
            .then((data) => {
                alert('회원탈퇴가 완료되었습니다.')
                window.location.href = '/';
            })
            .catch((error) => {
                alert('오류');
            })
    }
}