/*
s -> servicio
p -> promesa
f -> funcion
l -> local
*/

function borrarDeLocal (nombre) {
  localStorage.removeItem(nombre)
}

function obtenerDeLocal (nombre) {
  const itemStr = localStorage.getItem(nombre)
  if (!itemStr) {
    return null
  }
  const item = JSON.parse(itemStr)
  const now = new Date()
  if (now.getTime() > item.expire) {
    localStorage.removeItem(nombre)
    return null
  }
  return item
}

function guardarEnLocal (nombre, valor, ttl) {
  localStorage.removeItem(nombre)
  const now = new Date()
  const item = {
    value: valor,
    expire: now.getTime() + ttl
  }
  localStorage.setItem(nombre, JSON.stringify(item))
}

export const llaveroService = {
  borrarDeLocal,
  obtenerDeLocal,
  guardarEnLocal
}
