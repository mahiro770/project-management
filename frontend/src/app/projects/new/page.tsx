import { createProject } from "@/lib/api";
import ProjectForm from "@/components/ProjectForm";

export default function NewProjectPage() {
  async function handleCreate(data: {
    title: string;
    clientName: string;
    requiredSkills: string;
    location: string;
    priceMin: string;
    priceMax: string;
    status: string;
  }) {
    "use server";
    await createProject({
      title: data.title,
      clientName: data.clientName,
      requiredSkills: data.requiredSkills,
      location: data.location,
      priceMin: data.priceMin ? Number(data.priceMin) : 0,
      priceMax: data.priceMax ? Number(data.priceMax) : 0,
      status: data.status,
    });
  }

  return (
    <div className="max-w-2xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-800 mb-6">案件登録</h1>
      <ProjectForm onSubmit={handleCreate} submitLabel="登録する" />
    </div>
  );
}
