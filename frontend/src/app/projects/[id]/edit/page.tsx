import { notFound } from "next/navigation";
import { getProject, updateProject } from "@/lib/api";
import ProjectForm from "@/components/ProjectForm";

type Props = { params: Promise<{ id: string }> };

export default async function EditProjectPage({ params }: Props) {
  const { id } = await params;
  const project = await getProject(Number(id)).catch(() => null);

  if (!project) notFound();

  async function handleUpdate(data: {
    title: string;
    clientName: string;
    requiredSkills: string;
    location: string;
    priceMin: string;
    priceMax: string;
    status: string;
  }) {
    "use server";
    await updateProject(Number(id), {
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
      <h1 className="text-2xl font-bold text-gray-800 mb-6">案件編集</h1>
      <ProjectForm
        initialData={project}
        onSubmit={handleUpdate}
        submitLabel="更新する"
      />
    </div>
  );
}
