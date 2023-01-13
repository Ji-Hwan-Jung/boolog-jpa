const toastContainer = document.querySelector('#toast');
const toastResult = document.querySelector('#toastResult');
const toast = new bootstrap.Toast(toastContainer);


function updateName() {
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
    .then((response) => {
        response.text().then((result) => {
            console.log(result);
            toastResult.textContent = result;
            document.querySelector('#profile').setAttribute('href', '/@' + this.username.value);
            toast.show()
        })
    })
    .catch((error) => {
        alert('오류 발생');
        window.location.href = '/setting';
    });
}

function updateIntro() {
    const introduction = document.querySelector('#introduction');

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
    .then((response) => {
        response.text().then((result) => {
            console.log(result);
            toastResult.textContent = result;
            toast.show();
        })
    })
    .catch((error) => {
        alert('오류 발생');
        window.location.href = '/setting';
    });
}

function withdrawal() {

    let result = confirm('회원탈퇴를 진행하시겠습니까?');

    if (result) {
        fetch("/setting/withdrawal", {
                method: 'DELETE',
                headers: {
                    'Accept': 'text/plain'
                }
            })
            .then((response) => {
                response.text().then((result) => {
                    alert(result);
                })
                if (response.ok) {
                    window.location.href = '/';
                }
            })
            .catch((error) => {
                alert('Client Error');
            })
    }
}