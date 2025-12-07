<template>
  <div class="share-management">
    <h2>Share Management</h2>
    
    <v-data-table
      :headers="headers"
      :items="items"
      :loading="loading"
      :items-per-page="10"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Shared Items</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="loadItems">
            <v-icon left>mdi-refresh</v-icon>
            Refresh
          </v-btn>
        </v-toolbar>
      </template>

      <template v-slot:item.actions="{ item }">
        <v-icon
          small
          class="mr-2"
          @click="deleteItem(item.id)"
          :disabled="loading"
        >
          mdi-delete
        </v-icon>
      </template>

      <template v-slot:no-data>
        <div class="text-center py-4">
          <p>No shared items found</p>
          <v-btn color="primary" @click="loadItems" class="mt-2">
            <v-icon left>mdi-refresh</v-icon>
            Refresh
          </v-btn>
        </div>
      </template>

      <template v-slot:loading>
        <v-progress-linear
          indeterminate
          color="primary"
          class="my-4"
        ></v-progress-linear>
      </template>
    </v-data-table>

    <v-snackbar v-model="snackbar.show" :color="snackbar.color" timeout="3000">
      {{ snackbar.message }}
    </v-snackbar>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import ShareService from '@/factories/ShareService';

export default {
  name: 'ShareManagement',
  
  setup() {
    const store = useStore();
    const router = useRouter();
    
    const loading = ref(false);
    const items = ref([]);
    const snackbar = ref({
      show: false,
      message: '',
      color: 'success'
    });

    const headers = [
      { text: 'ID', value: 'id' },
      { text: 'Video Title', value: 'videoTitle' },
      { text: 'Shared With', value: 'recipientEmail' },
      { text: 'Shared At', value: 'sharedAt' },
      { text: 'Actions', value: 'actions', sortable: false, align: 'end' },
    ];

    const loadItems = async () => {
      try {
        loading.value = true;
        // Get current user ID from store
        const userId = store.state.auth.user?.id;
        if (!userId) {
          throw new Error('User not authenticated');
        }
        
        const response = await ShareService.getSharesByUser(userId);
        items.value = response.data.map(share => ({
          id: share.id,
          videoTitle: share.video?.title || 'Unknown',
          recipientEmail: share.recipientEmail,
          sharedAt: new Date(share.sharedAt).toLocaleString(),
        }));
      } catch (error) {
        console.error('Failed to load shares:', error);
        showSnackbar(error.response?.data?.message || 'Failed to load shares', 'error');
      } finally {
        loading.value = false;
      }
    };

    const deleteItem = async (id) => {
      if (!confirm('Are you sure you want to delete this share?')) {
        return;
      }
      
      try {
        loading.value = true;
        await ShareService.deleteShare(id);
        items.value = items.value.filter(item => item.id !== id);
        showSnackbar('Share deleted successfully', 'success');
      } catch (error) {
        console.error('Failed to delete share:', error);
        showSnackbar(error.response?.data?.message || 'Failed to delete share', 'error');
      } finally {
        loading.value = false;
      }
    };

    const showSnackbar = (message, color = 'success') => {
      snackbar.value = {
        show: true,
        message,
        color
      };
    };

    onMounted(() => {
      loadItems();
    });

    return {
      loading,
      items,
      headers,
      snackbar,
      loadItems,
      deleteItem,
    };
  },
};
</script>

<style scoped>
.share-management {
  padding: 20px;
}
</style>
