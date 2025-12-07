import { ref } from 'vue';
import ShareService from '../factories/ShareService';

export default function useShare() {
  const shareHistory = ref([]);
  const loading = ref(false);
  const error = ref(null);

  const shareVideo = async (videoId, emails) => {
    try {
      loading.value = true;
      error.value = null;
      
      const response = await ShareService.createShare({
        videoId,
        emails: Array.isArray(emails) ? emails : [emails]
      });
      
      return response.data;
    } catch (err) {
      error.value = err.response?.data?.message || 'Failed to share video';
      throw error.value;
    } finally {
      loading.value = false;
    }
  };

  const loadShareHistory = async (userId) => {
    try {
      loading.value = true;
      error.value = null;
      
      const response = await ShareService.getSharesByUser(userId);
      shareHistory.value = response.data;
      
      return shareHistory.value;
    } catch (err) {
      error.value = 'Failed to load share history';
      throw error.value;
    } finally {
      loading.value = false;
    }
  };

  return {
    shareHistory,
    loading,
    error,
    shareVideo,
    loadShareHistory
  };
}
