import BaseJavaService from './BaseJavaService';

export default class JavaShareService extends BaseJavaService {
  constructor() {
    super('/shares');
  }

  async getAllShares() {
    return this.get('');
  }

  async createShare(data) {
    return this.post('', data);
  }

  async deleteShare(id) {
    return this.delete(`/${id}`);
  }

  async getSharesByVideo(videoId) {
    return this.get(`/video/${videoId}`);
  }

  async getSharesByUser(userId) {
    return this.get(`/user/${userId}`);
  }
}
