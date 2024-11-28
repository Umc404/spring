console.log("boardComment.js connect test");

// detail 에 댓글 뿌리기
async function getCommentListFromServer(bno, page) {
    try {
        const resp = await fetch('/comment/list/'+bnoVal+'/'+page);
        const result = await resp.json();
        return result;
    } catch(error) {
        console.log(error);
    }
}

document.getElementById("cmtAdd").addEventListener('click', ()=>{
    const cmtText = document.getElementById("cmtText");
    const cmtWriter = document.getElementById("cmtWriter");

    if(cmtText.value == null || cmtText.value == '') {
        alert("댓글이 비었습니다.");
        cmtText.focus();
        return false;
    }
    let cmtData = {
        bno: bnoVal,
        writer: cmtWriter.innerText,
        content: cmtText.value
    }
    console.log(cmtData);       // post 버튼 클릭했을 때 콘솔 출력
    postCommentToServer(cmtData).then(result => {
        if(result == '1') {
            alert("댓글 등록 완료");
            cmtText.value = "";
            spreadCommentList(bnoVal);

        }
    });
})


function spreadCommentList(bnoVal, page=1) {
    getCommentListFromServer(bnoVal, page).then(result => {
        console.log(result);
        const ul = document.getElementById("cmtListArea");

        if(result.cmtList.length > 0) {
            if(page == 1) {
                ul.innerHTML = "";      // 기존 샘플값 비우기
            }
            for(let cvo of result.cmtList) {
                let li = `<li data-cno="${cvo.cno}" class="list-group-item">`;
                li += `<div class="ms-2 me-auto">`;
                li += `<div class="fw-bold">${cvo.writer}</div>`;
                li += `${cvo.content} </div>`;
                li += `<span class="badge text-bg-primary rounded-pill">regDate</span>`;
                if( nickName == cvo.writer ) {
                    li += `<button type="button" class="btn btn-primary mod" data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`
                    li += `<button type="button" class="btn btn-danger del">삭제</button>`;
                }
                // 나중에 버튼태그 data-cno=${cvo.cno} 추가
                li += `</li>`;
                ul.innerHTML += li;
            }

            let moreBtn = document.getElementById("moreBtn");

            if(result.pgvo.pageNo < result.realEndPage) {
                moreBtn.style.visibility = "visible";
                moreBtn.dataset.page = page + 1;
            } else {
                moreBtn.style.visibility = "hidden";
            }
        } else {
            let li = `<li class="list-group-item">Comment List None.</li>`;
            ul.innerHTML = li;
        }

    })
}

// 더보기 , 삭제 버튼
document.addEventListener('click', (e)=> {
    console.log(e.target);
    if(e.target.id == 'moreBtn') {
        let page = parseInt(e.target.dataset.page);
        spreadCommentList(bnoVal, page);
    }
    if(e.target.classList.contains("del")) {
        let cno = e.target.closest('li').dataset.cno;
        removeCommentToServer(cno).then(result => {
            if(result > 0) {
                alert("댓글 삭제 성공");
                spreadCommentList(bnoVal);
            }
        })
    }
    if(e.target.classList.contains("mod")) {
        let li = e.target.closest('li');

        let modWriter = li.querySelector('.fw-bold').innerText;
        document.getElementById('cmtWriterMod').innerText = modWriter;
        let cmtText = li.querySelector('.fw-bold').nextSibling;             // 쿼리 값의 같은 부모의 다른 형제 값
        document.getElementById('cmtTextMod').value = cmtText.nodeValue;    // nodeValue 없이 처리하면 Object로 입력

        // 수정버튼 id = cmtModBtn dataset 달기
        document.getElementById('cmtModBtn').setAttribute("data-cno", li.dataset.cno);
    }
    if(e.target.id == 'cmtModBtn') {
        let cmtModData = {
            cno: e.target.dataset.cno,
            content: document.getElementById('cmtTextMod').value
        }
        console.log(cmtModData);
        updateCommentToServer(cmtModData).then(result => {
            if( result > 0 ) {
                alert('댓글 수정 성공');
            } else {
                alert('댓글 수정 실패');
            }
            document.querySelector('.btn-close').click();

            spreadCommentList(bnoVal);
        })
    }
})

async function updateCommentToServer(cmtModData) {
    try {
        const url = "/comment/update"
        const config = {
            method: "put",
            headers:{
                'content-type':'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtModData)
        }
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch(error) {
        console.log(error);
    }
}
/*
<li class="list-group-item">
        <div class="ms-2 me-auto">
          <div class="fw-bold">Comment name</div>
          Content
        </div>
        <span class="badge text-bg-primary rounded-pill">regDate</span>
      </li>
*/
async function removeCommentToServer(cno) {
    try {
        const url = "/comment/remove/" + cno;
        const config = {
            method: 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch(error) {
        console.log(error);
    }
}

// 객체 데이터 등록처리
async function postCommentToServer(cmtData) {
    try {
        const url = "/comment/post";
        const config = {
            method: "post",
            headers: {
                'content-type':'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        console.log(resp);
        const result = await resp.text();
        console.log(result);

        return result;
    } catch(error) {
        console.log(error);
    }
};

