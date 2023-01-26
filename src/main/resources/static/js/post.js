const description = document.querySelector('#description');
const title = document.querySelector('#title');
const tags = document.querySelector('#tags');
let content = '';
new Tagify(tags);

// 썸네일 관련
const thumbnail = document.querySelector('#thumbnail');
const thumbnailUpload = document.querySelector('#thumbnailUpload');
const closeModal = document.querySelector('#closeModal');

closeModal.addEventListener('click', deleteThumbnail);

thumbnailUpload.addEventListener('change', function () {
    thumbnail.src = URL.createObjectURL(thumbnailUpload.files[0]);
});

function deleteThumbnail() {
    thumbnailUpload.value = '';
    thumbnail.src = 'http://chiye1890.dothome.co.kr/img/no_thumbnail.png';
}

// 게시글 저장
function savePost() {
    const tags_string = (tags.value.length > 0) ? JSON.parse(tags.value).map((e) => {
        return e.value.toLowerCase();
    }).join(',') : '';

    if (editor.isMarkdownMode()) {
        content = editor.getMarkdown();
    }

    if (editor.isWysiwygMode()) {
        content = editor.getHTML();
    }

    if (title.value.length < 1) {
        alert('제목을 입력해주세요.');
        return;
    }

    if (content.length < 1) {
        alert('내용을 입력해주세요.');
        return;
    }

    const formData = new FormData();
    //formData.append('thumbnail', 'http://chiye1890.dothome.co.kr/img/Spring_Logo.svg');
    formData.append('description', description.value.trim());
    formData.append('title', title.value.trim());
    formData.append('content', content.trim());
    formData.append('tags', tags_string);

    fetch("/post/write", {
        method: 'POST',
        body: formData,
        headers: {
            'Accept' : 'application/json'
        }
    })
        .then((response => {
            if (response.ok) {
                response.text().then((result) => {
                    window.location.href = `/post/${result}`;
                });
            } else {
                response.json().then((result) => {
                    alert(result.message);
                });
            }
        }))
        .catch((error) => {
            console.error('실패', error);
        });
}

// 게시글 수정
function updatePost(post_id) {
    const tags_string = (tags.value.length > 0) ? JSON.parse(tags.value).map((e) => {
        return e.value.toLowerCase();
    }).join(',') : '';

    if (editor.isMarkdownMode()) {
        content = editor.getMarkdown();
    }

    if (editor.isWysiwygMode()) {
        content = editor.getHTML();
    }

    if (title.value.length < 1) {
        alert('제목을 입력해주세요.');
        return;
    }

    if (content.length < 1) {
        alert('내용을 입력해주세요.');
        return;
    }

    const formData = new FormData();
    formData.append('description', description.value.trim());
    formData.append('title', title.value.trim());
    formData.append('content', content.trim());
    formData.append('tags', tags_string);

    fetch("/post/" + post_id + "/edit", {
        method: 'PUT',
        body: formData,
        headers: {
            'Accept' : 'application/json'
        }
    })
        .then((response) => {
            if (response.ok) {
                response.text().then((result) => {
                    window.location.href = `/post/${result}`;
                });
            } else {
                response.json().then((result) => {
                    alert(result.message);
                });
            }
        })
        .catch((error) => {
            console.error('실패', error);
        });
}

// 게시글 삭제
function deletePost(post_id) {

    const result = confirm('게시글을 삭제하시겠습니까?');

    if (result) {
        fetch("/post/" + post_id + "/delete", {
            method: 'DELETE',
            headers: {
                'Accept' : 'application/json'
            }
        })
            .then((response) => {
                if (response.ok) {
                    window.location.href = "/";
                } else {
                    response.json().then((result) => {
                        alert(result.message);
                    });
                }
            })
            .catch((error) => {
                console.error('실패', error);
            });
    }
}

// 좋아요 관련 로직
function thumbUpProc(post_id, is_liked) {

    const likedCnt = document.querySelector('#likedCnt');
    const likedStatus = document.querySelector('#likedStatus');

    if (is_liked) {
        fetch("/post/" + post_id + "/thumb-up-cancel", {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json'
            }
        })
            .then((response) => {
                if (response.ok) {
                    response.text().then((result) => {
                        likedStatus.classList.replace('btn-success', 'btn-outline-success');
                        likedStatus.setAttribute('onclick', `thumbUpProc(${post_id}, ${!is_liked})`);
                        likedCnt.textContent = result;
                    });
                } else {
                    response.json().then((result) => {
                        alert(result.message);
                    });
                }
            })
            .catch((error) => {
                alert('Client Error');
            })
    } else {
        fetch("/post/" + post_id + "/thumb-up", {
            method: 'POST',
            headers: {
                'Accept': 'application/json'
            }
        })
            .then((response) => {
                if (response.ok) {
                    response.text().then((result) => {
                        likedStatus.classList.replace('btn-outline-success', 'btn-success');
                        likedStatus.setAttribute('onclick', `thumbUpProc(${post_id}, ${!is_liked})`);
                        likedCnt.textContent = result;
                    });
                } else {
                    response.json().then((result) => {
                        alert(result.message);
                    })
                }
            })
            .catch((error) => {
                alert('Client Error');
            })
    }
}

function publish(mode, desc = '') {
    const description = document.querySelector('#description');
    const content = editor.isMarkdownMode() ? editor.getMarkdown() : editor.getHTML();

    if (mode === 'write') {
        description.value = content.substring(0, 150);
    }

    if (mode === 'edit') {
        description.value = desc;
    }
}