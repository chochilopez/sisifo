import { llaveroService } from 'src/helpers/llavero_service'
import axios from 'axios'
const API_URL = process.env.API_URL

/*
s -> servicio
p -> promesa
f -> funcion
l -> local
*/

function fEstaLogueado () {
  if (llaveroService.obtenerDeLocal('ehToken') === null) {
    return false
  } else {
    return true
  }
}

function obtenerAutoridades () {
  if (llaveroService.obtenerDeLocal('ehAutoridades')) { return llaveroService.obtenerDeLocal('ehAutoridades').value } else { return null }
}

function obtenerToken () {
  if (llaveroService.obtenerDeLocal('ehToken')) { return llaveroService.obtenerDeLocal('ehToken').value } else { return null }
}

function obtenerNombreUsuario () {
  if (llaveroService.obtenerDeLocal('ehNombreUsuario')) { return llaveroService.obtenerDeLocal('ehNombreUsuario').value } else { return null }
}

function spfIngresarServer (user) {
  return new Promise((resolve, reject) => {
    axios.post('https://vps-3450851-x.dattaweb.com:9088/api/autenticacion/ingresar', user)
      .then((response) => {
        resolve(response)
      })
      .catch((error) => {
        reject(error)
      })
  })
}

function spfIngresarDev (user) {
  return new Promise((resolve, reject) => {
    axios.post('http://localhost:9088/api/autenticacion/ingresar', user)
      .then((response) => {
        resolve(response)
      })
      .catch((error) => {
        reject(error)
      })
  })
}

function spfCheck () {
  return new Promise((resolve, reject) => {
    axios.get('https://vps-3450851-x.dattaweb.com:9088/api/calle/buscar-todas', {
      headers: {
        Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJDT05UUklCVVlFTlRFIjp0cnVlLCJFTVBMRUFETyI6dHJ1ZSwiSkVGRSI6dHJ1ZSwiQ0FQQVRBWiI6dHJ1ZSwic3ViIjoiamVmZUBtdW5pY3Jlc3BvLmdvYi5hciIsImlhdCI6MTY5NzExMDYzNiwiZXhwIjoxNjk3NzE1NDM2fQ.Kvaj8zd9iHdGiUEbyMtusRPEyk5Pu5Ip5rYzW_F86cM'
      }
    })
      .then((result) => {
        resolve(result)
      })
      .catch((error) => {
        reject(error)
      })
  })
}

function spfSalir () {
  return new Promise((resolve, reject) => {
    axios.get(API_URL + 'autenticacion/salir', {
      headers: {
        Authorization: 'Bearer ' + this.obtenerToken()
      }
    })
      .then((response) => {
        resolve(response)
      })
      .catch((error) => {
        reject(error)
      })
    llaveroService.borrarDeLocal('ehToken')
    llaveroService.borrarDeLocal('ehAutoridades')
    llaveroService.borrarDeLocal('ehNombreUsuario')
    localStorage.clear()
  })
}

export const autenticacionService = {
  fEstaLogueado,
  obtenerAutoridades,
  obtenerToken,
  obtenerNombreUsuario,
  spfIngresarServer,
  spfCheck,
  spfIngresarDev,
  spfSalir
}
