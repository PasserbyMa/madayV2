document.querySelectorAll('.post-link').forEach(link => {
    link.addEventListener('click', async (e) => {
        e.preventDefault();
        const res = await fetch(`/posts/${link.dataset.id}/data`);
        const post = await res.json();
        document.getElementById('modalCategory').textContent = post.category || '';
        document.getElementById('modalTitle').textContent = post.title;
        document.getElementById('modalDate').textContent =
            post.createdAt ? new Date(post.createdAt).toLocaleDateString('ko-KR') : '';
        document.getElementById('modalContent').textContent = post.content;
        document.getElementById('modalFooter').innerHTML = isAuth ? `
            <a href="/posts/edit/${post.id}" class="btn">수정</a>
            <form action="/posts/delete/${post.id}" method="post" style="display:inline">
                <input type="hidden" name="${csrfParam}" value="${csrfToken}">
                <button type="submit" class="btn btn-danger">삭제</button>
            </form>` : '';
        document.getElementById('modalOverlay').classList.add('active');
    });
});

document.getElementById('modalClose').addEventListener('click', closeModal);
document.getElementById('modalOverlay').addEventListener('click', e => {
    if (e.target.id === 'modalOverlay') closeModal();
});
document.addEventListener('keydown', e => { if (e.key === 'Escape') closeModal(); });

function closeModal() {
    document.getElementById('modalOverlay').classList.remove('active');
}

if (isAuth) {
    document.getElementById('sqlRun').addEventListener('click', async () => {
        const sql = document.getElementById('sqlInput').value.trim();
        if (!sql) return;
        const resultDiv = document.getElementById('sqlResult');
        resultDiv.innerHTML = '<div class="sql-loading">실행 중...</div>';
        try {
            const res = await fetch('/sql/execute', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
                body: JSON.stringify({ sql })
            });
            const data = await res.json();
            if (data.type === 'error') {
                resultDiv.innerHTML = `<div class="sql-error">ERROR: ${data.message}</div>`;
            } else if (data.type === 'select') {
                resultDiv.innerHTML = renderTable(data.rows, data.count);
            } else {
                resultDiv.innerHTML = `<div class="sql-ok">OK — ${data.affected ?? 0}행 영향받음</div>`;
            }
        } catch (e) {
            resultDiv.innerHTML = `<div class="sql-error">ERROR: ${e.message}</div>`;
        }
    });

    document.getElementById('sqlClear').addEventListener('click', () => {
        document.getElementById('sqlInput').value = '';
        document.getElementById('sqlResult').innerHTML = '';
    });
}

function renderTable(rows, count) {
    if (!rows || rows.length === 0) return '<div class="sql-ok">결과 없음 (0행)</div>';
    const cols = Object.keys(rows[0]);
    let html = `<div class="sql-count">${count}행 반환</div><div class="sql-table-wrap"><table class="sql-table"><thead><tr>`;
    cols.forEach(c => html += `<th>${c}</th>`);
    html += '</tr></thead><tbody>';
    rows.forEach(row => {
        html += '<tr>';
        cols.forEach(c => html += `<td>${row[c] ?? 'NULL'}</td>`);
        html += '</tr>';
    });
    return html + '</tbody></table></div>';
}