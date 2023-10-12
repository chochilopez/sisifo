import { uid, format, colors, date } from 'quasar'
const { humanStorageSize } = format
const { getPaletteColor } = colors
const timeStamp = Date.now()

function difBetweenDates (fecha1, fecha2, unidad) {
  // seconds - minutes - hours - days - months - years
  return date.getDateDiff(fecha1, fecha2, unidad)
}

function isValidDate (fecha) {
  return date.isValid(fecha)
}

function getColor (color) {
  return getPaletteColor(color)
}

function getAsset (image) {
  return `${process.env.APP_MULTIMEDIA_URL}${image}`
}

function getUid () {
  return uid()
}

function getSize (number) {
  return humanStorageSize(number)
}

function getToday () {
  return date.formatDate(timeStamp, 'dddd-DD-MMMM-YYYY')
}

function getDateWithFormat (fechaSql) {
  const MESES = [
    'Enero',
    'Febrero',
    'Marzo',
    'Abril',
    'Mayo',
    'Junio',
    'Julio',
    'Agosto',
    'Septiembre',
    'Octubre',
    'Noviembre',
    'Diciembre'
  ]
  const f = new Date(fechaSql)
  return (
    f.getDate() + ' de ' + MESES[f.getMonth()] + ' del ' + f.getFullYear()
  )
}

function getByIdInArray (arreglo, id) {
  if (arreglo.length > 0 && id > 0) {
    return arreglo.find(function (item) {
      return item.id === id
    })
  }
}

function delByIdInArray (arreglo, id) {
  if (arreglo.length > 0 && id > 0) {
    return arreglo.filter(function (item) {
      return item.id !== id
    })
  }
}

function letterForNumber (numero) {
  let variable = ''
  switch (numero) {
    case '1':
      variable = 'a'
      break
    case '2':
      variable = 'b'
      break
    case '3':
      variable = 'c'
      break
    case '4':
      variable = 'd'
      break
    case '5':
      variable = 'e'
      break
    case '6':
      variable = 'f'
      break
    case '7':
      variable = 'g'
      break
    case '8':
      variable = 'h'
      break
    case '9':
      variable = 'i'
      break
    case '10':
      variable = 'j'
      break
    case '11':
      variable = 'k'
      break
    case '12':
      variable = 'l'
      break
    case '13':
      variable = 'm'
      break
    case '14':
      variable = 'n'
      break
    case '15':
      variable = 'o'
      break
    case '16':
      variable = 'p'
      break
    case '17':
      variable = 'q'
      break
    case '18':
      variable = 'r'
      break
    case '19':
      variable = 's'
      break
    case '20':
      variable = 't'
      break
    case '21':
      variable = 'u'
      break
    case '22':
      variable = 'v'
      break
    case '23':
      variable = 'w'
      break
    case '24':
      variable = 'x'
      break
    case '25':
      variable = 'y'
      break
    case '26':
      variable = 'z'
      break
  }
  return variable
}

export const helper = {
  difBetweenDates,
  isValidDate,
  getAsset,
  getColor,
  getUid,
  getSize,
  getToday,
  getDateWithFormat,
  getByIdInArray,
  delByIdInArray,
  letterForNumber
}
