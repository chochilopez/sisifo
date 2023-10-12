<template>
  <q-page class="flex flex-center">
    <img
      alt="Quasar logo"
      src="~assets/quasar-logo-vertical.svg"
      style="width: 200px; height: 200px"
    >

  </q-page>
</template>

<script>
import { defineComponent } from 'vue'
import { autenticacionService } from '../services/autenticacion_service'

export default defineComponent({
  name: 'IndexPage',

  setup () {
    afDoLogin()

    afCheck()

    async function afDoLogin () {
      try {
        const user = {
          username: 'jefe@municrespo.gob.ar',
          password: 'contraseña'
        }
        const result = await autenticacionService.spfIngresarServer(user)
        // const result = await autenticacionService.spfIngresarDev(user)
        if (result.status === 200) {
          console.info(result)
        } else {
          console.error(result)
        }
      } catch (err) {
        console.error('Error: ' + err)
      }
    }

    async function afCheck () {
      try {
        const result = await autenticacionService.spfCheck()
        if (result.status === 200) {
          console.info(result)
        } else {
          console.error(result)
        }
      } catch (err) {
        console.error('Error: ' + err)
      }
    }

    return {

    }
  }
})
</script>
