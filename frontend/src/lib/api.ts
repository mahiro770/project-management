import { Project } from "@/types/project";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080";

export async function getProjects(): Promise<Project[]> {
  const res = await fetch(`${API_BASE_URL}/projects`, { cache: "no-store" });
  if (!res.ok) throw new Error("案件一覧の取得に失敗しました");
  return res.json();
}

export async function getProject(id: number): Promise<Project> {
  const res = await fetch(`${API_BASE_URL}/projects/${id}`, { cache: "no-store" });
  if (!res.ok) throw new Error("案件の取得に失敗しました");
  return res.json();
}

export async function createProject(data: Omit<Project, "id" | "createdAt" | "updatedAt">): Promise<void> {
  const res = await fetch(`${API_BASE_URL}/projects`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error("案件の登録に失敗しました");
}

export async function updateProject(id: number, data: Partial<Omit<Project, "id" | "createdAt" | "updatedAt">>): Promise<void> {
  const res = await fetch(`${API_BASE_URL}/projects/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error("案件の更新に失敗しました");
}

export async function deleteProject(id: number): Promise<void> {
  const res = await fetch(`${API_BASE_URL}/projects/${id}`, { method: "DELETE" });
  if (!res.ok) throw new Error("案件の削除に失敗しました");
}
