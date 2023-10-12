import { Notify, Loading } from 'quasar'
/*
s -> servicio
p -> promesa
f -> funcion
l -> local
*/

function infoAlerta (string) {
  Notify.create({
    progress: true,
    message: string,
    position: 'bottom',
    color: 'warning',
    textColor: 'black',
    icon: 'warning',
    timeout: 10000,
    closeBtn: 'CERRAR',
    multiLine: true
  })
}

function infoExito (string) {
  Notify.create({
    progress: true,
    message: string,
    position: 'bottom',
    color: 'positive',
    textColor: 'black',
    icon: 'check_circle',
    timeout: 10000,
    closeBtn: 'CERRAR',
    multiLine: true
  })
}

function infoError (string) {
  Notify.create({
    progress: true,
    message: string,
    position: 'bottom',
    color: 'negative',
    textColor: 'black',
    icon: 'error',
    timeout: 10000,
    closeBtn: 'CERRAR',
    multiLine: true
  })
}

function notificarError (string) {
  Notify.create({
    progress: true,
    message: string,
    position: 'top-right',
    color: 'negative',
    icon: 'error',
    timeout: 5000,
    multiLine: true
  })
}

function notificarAlerta (string) {
  Notify.create({
    progress: true,
    message: string,
    position: 'top-right',
    color: 'warning',
    icon: 'warning',
    timeout: 5000,
    multiLine: true
  })
}

function notificarExito (string) {
  Notify.create({
    progress: true,
    message: string,
    position: 'top-right',
    color: 'positive',
    icon: 'check_circle',
    timeout: 5000,
    multiLine: true
  })
}

function loadingScreen (string) {
  Loading.show({
    message: string,
    delay: 400 // ms
  })
}

export const notificarService = {
  notificarError,
  notificarAlerta,
  notificarExito,
  loadingScreen,
  infoAlerta,
  infoError,
  infoExito
}
