const API_BASE = '/api/missions';

async function loadMissions() {
    const tbody = document.getElementById('missionsTableBody');
    tbody.innerHTML = '<tr><td colspan="7" class="loading">Загрузка...</td></tr>';

    try {
        const response = await fetch(API_BASE);
        const missions = await response.json();

        if (!missions.length) {
            tbody.innerHTML = '<tr><td colspan="7" class="loading">Нет загруженных миссий</td></tr>';
            return;
        }

        tbody.innerHTML = '';
        missions.forEach(mission => {
            const row = tbody.insertRow();
            row.insertCell(0).textContent = mission.id;
            row.insertCell(1).textContent = mission.missionId;
            row.insertCell(2).textContent = mission.date;
            row.insertCell(3).textContent = mission.location;
            row.insertCell(4).textContent = mission.outcome;
            row.insertCell(5).textContent = mission.damageCost.toLocaleString() + ' йен';

            const actionCell = row.insertCell(6);
            const deleteBtn = document.createElement('button');
            deleteBtn.textContent = '🗑️ Удалить';
            deleteBtn.className = 'btn-delete';
            deleteBtn.onclick = (e) => {
                e.stopPropagation();
                confirmDelete(mission.id, mission.missionId);
            };
            actionCell.appendChild(deleteBtn);

            row.style.cursor = 'pointer';
            row.addEventListener('click', (e) => {
                if (e.target !== deleteBtn) {
                    document.getElementById('missionSelect').value = mission.id;
                    document.getElementById('missionSelect').dispatchEvent(new Event('change'));
                }
            });
        });

        updateMissionSelect(missions);

    } catch (error) {
        console.error('Ошибка загрузки:', error);
        tbody.innerHTML = '<tr><td colspan="7" class="loading">Ошибка загрузки</td></tr>';
    }
}

function confirmDelete(id, missionId) {
    if (confirm(`Удалить миссию "${missionId}"? Это действие нельзя отменить.`)) {
        deleteMission(id);
    }
}

async function deleteMission(id) {
    try {
        const response = await fetch(`${API_BASE}/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            const resultDiv = document.getElementById('uploadResult');
            resultDiv.className = 'result-message success';
            resultDiv.textContent = `✅ Миссия успешно удалена`;

            const selectedId = document.getElementById('missionSelect').value;
            if (selectedId == id) {
                document.getElementById('missionSelect').value = '';
                document.getElementById('reportCard').style.display = 'none';
            }

            loadMissions();

            setTimeout(() => {
                if (resultDiv.textContent.includes('успешно удалена')) {
                    resultDiv.textContent = '';
                    resultDiv.className = 'result-message';
                }
            }, 3000);

        } else {
            const error = await response.text();
            showError(`Ошибка удаления: ${error}`);
        }
    } catch (error) {
        console.error('Ошибка удаления:', error);
        showError(`Ошибка удаления: ${error.message}`);
    }
}

function showError(message) {
    const resultDiv = document.getElementById('uploadResult');
    resultDiv.className = 'result-message error';
    resultDiv.textContent = `❌ ${message}`;
    setTimeout(() => {
        if (resultDiv.textContent === `❌ ${message}`) {
            resultDiv.textContent = '';
            resultDiv.className = 'result-message';
        }
    }, 5000);
}

function updateMissionSelect(missions) {
    const select = document.getElementById('missionSelect');
    const currentValue = select.value;

    select.innerHTML = '<option value="">-- Выберите миссию --</option>';
    missions.forEach(mission => {
        const option = document.createElement('option');
        option.value = mission.id;
        option.textContent = `${mission.missionId} (${mission.date})`;
        select.appendChild(option);
    });

    if (currentValue && Array.from(select.options).some(opt => opt.value === currentValue)) {
        select.value = currentValue;
    }
}

async function uploadMission(file) {
    const formData = new FormData();
    formData.append('file', file);

    const resultDiv = document.getElementById('uploadResult');
    resultDiv.className = 'result-message';
    resultDiv.textContent = 'Загрузка...';

    try {
        const response = await fetch(`${API_BASE}/upload`, {
            method: 'POST',
            body: formData
        });

        const data = await response.json();

        if (response.ok) {
            resultDiv.className = 'result-message success';
            resultDiv.textContent = `✅ Миссия "${data.missionId}" успешно загружена (ID: ${data.id})`;
            loadMissions();
            document.getElementById('fileInput').value = '';
        } else {
            resultDiv.className = 'result-message error';
            resultDiv.textContent = `❌ Ошибка: ${data.error || 'Неизвестная ошибка'}`;
        }
    } catch (error) {
        resultDiv.className = 'result-message error';
        resultDiv.textContent = `❌ Ошибка: ${error.message}`;
    }
}

async function generateReport() {
    const missionId = document.getElementById('missionSelect').value;
    const reportType = document.getElementById('reportTypeSelect').value;

    if (!missionId) {
        alert('Выберите миссию');
        return;
    }

    const reportCard = document.getElementById('reportCard');
    const reportContent = document.getElementById('reportContent');

    reportCard.style.display = 'block';
    reportContent.textContent = 'Генерация отчёта...';

    try {
        const response = await fetch(`${API_BASE}/${missionId}/report?type=${reportType}`);

        if (response.ok) {
            const text = await response.text();
            reportContent.textContent = text;
        } else {
            reportContent.textContent = `Ошибка: ${response.status} ${response.statusText}`;
        }
    } catch (error) {
        reportContent.textContent = `Ошибка: ${error.message}`;
    }
}

function copyReport() {
    const reportContent = document.getElementById('reportContent');
    if (reportContent.textContent && reportContent.textContent !== 'Генерация отчёта...') {
        navigator.clipboard.writeText(reportContent.textContent);
        alert('Отчёт скопирован в буфер обмена');
    }
}

function downloadReport() {
    const reportContent = document.getElementById('reportContent');
    const missionSelect = document.getElementById('missionSelect');
    const reportType = document.getElementById('reportTypeSelect');

    if (reportContent.textContent && reportContent.textContent !== 'Генерация отчёта...') {
        const missionId = missionSelect.options[missionSelect.selectedIndex]?.text || 'report';
        const type = reportType.options[reportType.selectedIndex]?.text || 'report';
        const blob = new Blob([reportContent.textContent], { type: 'text/plain' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `mission_${missionId}_${type}.txt`;
        a.click();
        URL.revokeObjectURL(url);
    }
}

document.getElementById('uploadBtn').addEventListener('click', () => {
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];
    if (file) {
        uploadMission(file);
    } else {
        alert('Выберите файл');
    }
});

document.getElementById('generateReportBtn').addEventListener('click', generateReport);
document.getElementById('refreshBtn').addEventListener('click', loadMissions);
document.getElementById('copyReportBtn').addEventListener('click', copyReport);
document.getElementById('downloadReportBtn').addEventListener('click', downloadReport);

loadMissions();