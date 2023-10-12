export const messageStatusEnum = {
  MESSAGE_STATUS_QUEUED: 'MESSAGE_STATUS_QUEUED', // SQL Integer 0 - El estudio esta marcado para ser enviado por whatsapp (el número debe estar formado por +549xxxxxxxxxx)
  MESSAGE_STATUS_OUTDATED: 'MESSAGE_STATUS_OUTDATED', // Integer 1 - El estudio es previo a la implementacion de whatsapp, no será tenido en cuenta
  MESSAGE_STATUS_SENDED: 'MESSAGE_STATUS_SENDED', // Integer 2 - El mensaje correspondiente al estudio fue enviado, el proximo paso es chequear el estado del envio
  MESSAGE_STATUS_NOT_REQUIRED: 'MESSAGE_STATUS_NOT_REQUIRED', // Integer 3 - El estudio es marcado para NO ser enviado mediante whatsapp
  MESSAGE_STATUS_ERROR: 'MESSAGE_STATUS_ERROR', // Integer 4 - Ocurrio un error al intentar enviar el whatsapp
  MESSAGE_STATUS_CELLPHONE_ERROR: 'MESSAGE_STATUS_CELLPHONE_ERROR', // Integer 5 - El numero de celular esta mal formado. Sin espacio, sin guiones, sin 0 y 15, anteponiendo +549
  MESSAGE_STATUS_REQUIRED: 'MESSAGE_STATUS_REQUIRED', // Integer 6 - El estudio fue requerido
  MESSAGE_STATUS_NOTIFICATED: 'MESSAGE_STATUS_NOTIFICATED', // Integer 7 - Se notifico al cliente que se encuentra listo el estudio para descargar. Si presiona si el estudio sera descargado.
  MESSAGE_STATUS_OVERDUE: 'MESSAGE_STATUS_OVERDUE' // Integer 8 - Se notifico al cliente que se encuentra listo el estudio para descargar, pero ignoró el mensaje por mas de 24 horas
}
