<script language="javascript">
    var i;
    for(i=0; i < 12; i++){
        window.Pencil.drawCircle(50 + i * 20, 100, 20);
    }
    window.Pencil.drawLine(20, 100, 400, 100);
    for(i=0; i < 12; i++){
            window.Pencil.drawCircle(200, 150 +20 * i, 20);
    }
    window.Pencil.drawLine(200, 130, 200, 450);
    window.Pencil.post();
</script>