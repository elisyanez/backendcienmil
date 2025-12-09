1. EJECUTAR BACKEND:
   - Abrir proyecto Spring Boot
   - Ejecutar: mvn spring-boot:run
   - Verificar: http://localhost:8080/api/test/ping

//Para APP EN ANDROID

2. EJECUTAR NGROK:
   - Doble clic en: start_ngrok.bat
   - Esperar mensaje: "Forwarding https://xxxx.sa.ngrok.io"

3. CONFIGURAR ANDROID:
   - Abrir app e ir a configuracion (icono de engranaje en barra superior)
   - Cambiar URL: https://XXXX.sa.ngrok.io/api/
   - Guardar URL y reiniciar aplicacion.

4. VERIFICAR:
   - Celular y PC en MISMA red WiFi

=== TROUBLESHOOTING ===
- Error "tunnel session failed": Revisar conexión internet
- Error "failed to start tunnel": Puerto 8080 ocupado
- No conecta: Verificar firewall Windows
