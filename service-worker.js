const CACHE_NAME = "smartapp-v2";
const URLS_TO_CACHE = [
  "/smartapp/",
  "/smartapp/App/painelexibidor1.html",
  "/smartapp/App/audio/logo.png"
];

// Instalação (cache inicial)
self.addEventListener("install", event => {
  event.waitUntil(
    caches.open(CACHE_NAME).then(cache => cache.addAll(URLS_TO_CACHE))
  );
});

// Resposta a requisições
self.addEventListener("fetch", event => {
  event.respondWith(
    caches.match(event.request).then(response => {
      return response || fetch(event.request);
    })
  );
});
