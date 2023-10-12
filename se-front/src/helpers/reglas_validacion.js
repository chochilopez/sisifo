const reglas = {
  requerido: (value) => !!value || 'Por favor ingresa algo',
  min3: (value) => value.length >= 8 || 'Al menos 3 caracteres',
  min8: (value) => value.length >= 8 || 'Al menos 8 caracteres',
  min100: (value) => value.length >= 100 || 'Al menos 100 caracteres',
  max10: (value) => value.length <= 10 || 'Cómo máximo 10 carácteres',
  max16: (value) => value.length <= 16 || 'Cómo máximo 16 carácteres',
  max50: (value) => value.length <= 50 || 'Cómo máximo 50 carácteres',
  max100: (value) => value.length <= 100 || 'Cómo máximo 100 carácteres',
  max500: (value) => value.length <= 500 || 'Cómo máximo 500 carácteres',
  sinSignos: (value) => {
    const pattern = /^[A-Za-z0-9]+$/
    return pattern.test(value) || 'Debe ingresar solo letras y números'
  },
  email: (value) => !value || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(value) || 'El e-mail debe ser válido'
}

export const reglasValidacion = {
  reglas
}
