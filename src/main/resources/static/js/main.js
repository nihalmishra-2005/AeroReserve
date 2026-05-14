document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.alert:not(.alert-persistent)').forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity .5s,transform .5s';
            alert.style.opacity = '0';
            setTimeout(() => alert.remove(), 500);
        }, 4000);
    });
    const si = document.getElementById('tableSearch');
    if (si) si.addEventListener('input', function() {
        const q = this.value.toLowerCase();
        document.querySelectorAll('.ar-table tbody tr').forEach(r => r.style.display = r.textContent.toLowerCase().includes(q)?'':'none');
    });
    // Aisle gaps for seat map
    ['.seat-grid-eco C','.seat-grid-prem B'].forEach(rule => {
        const [sel, endChar] = rule.split(' ');
        const grid = document.querySelector(sel);
        if (grid) grid.querySelectorAll('.seat-btn').forEach(s => {
            if (s.dataset.seatNum?.endsWith(endChar)) { const g=document.createElement('div'); g.style.width='18px'; s.after(g); }
        });
    });
    // Arrival time auto-compute
    const dep = document.querySelector('[name="departureTime"]');
    const dur = document.querySelector('[name="durationMinutes"]');
    const arr = document.querySelector('[name="arrivalTime"]');
    const computeArr = () => {
        if (dep?.value && dur?.value && arr) {
            const d = new Date(dep.value); d.setMinutes(d.getMinutes()+parseInt(dur.value));
            const p = n => String(n).padStart(2,'0');
            arr.value = `${d.getFullYear()}-${p(d.getMonth()+1)}-${p(d.getDate())}T${p(d.getHours())}:${p(d.getMinutes())}`;
        }
    };
    dep?.addEventListener('change', computeArr);
    dur?.addEventListener('input', computeArr);
    // Stagger animate
    document.querySelectorAll('.flight-result-card,.stat-card,.kpi-card,.booking-history-item,.route-card').forEach((c,i) => {
        c.style.cssText += `opacity:0;transform:translateY(16px);transition:opacity .4s ease ${i*.07}s,transform .4s ease ${i*.07}s`;
        requestAnimationFrame(()=>requestAnimationFrame(()=>{ c.style.opacity='1'; c.style.transform='translateY(0)'; }));
    });
    document.querySelectorAll('[data-bs-toggle="tooltip"]').forEach(el => new bootstrap.Tooltip(el));
});
