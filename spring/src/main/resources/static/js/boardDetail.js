console.log("boardDetail.js connect test");

document.getElementById("listBtn").addEventListener('click',()=> {
    // 리스트로 이동
    location.href="/board/list";
});

document.getElementById("modBtn").addEventListener('click', ()=>{
    // title, content 의 readonly 해지
    document.getElementById('title').readOnly = false;
    document.getElementById('content').readOnly = false;
    document.getElementById('trigger').disabled = false;

    // modBtn, delBtn 임시 삭제.
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();

    // modBtn => submit 버튼으로 변경 추가
    let modBtn = document.createElement('button');  // <button></button>
        modBtn.setAttribute('type','submit')      // <button type="submit"></button>
        modBtn.classList.add('btn','btn-outline-warning');
        modBtn.innerText="submit";
        // <button type="submit" class="btn btn-outline-warning">submit</button>

    document.getElementById('modForm').appendChild(modBtn);

    let fileDelBtn = document.querySelectorAll(".file-x");
    console.log(fileDelBtn);
    for(let delBtn of fileDelBtn) {
        delBtn.disabled = false;
    }

});

document.getElementById("delBtn").addEventListener('click', ()=>{
    document.getElementById('delForm').submit();
})

document.addEventListener('click', (e)=>{
    // console.log(e.target);

    if(e.target.classList.contains('file-x')) {
        let uuid = e.target.dataset.uuid;
        removeFileToServer(uuid).then(result => {
            if(result =='1') {
                alert("파일삭제 성공");
                e.target.closest('li').remove();
            }
        })
    }
})

